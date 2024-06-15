package com.ekko.processor.viewholder

import com.ekko.ksp.annotation.PagingViewHolder
import com.ekko.processor.exception.KSPException
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import kotlin.properties.Delegates

class ViewHolderProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    private val visitor = BuilderVisitor()

    private val funSpecs = mutableListOf<FunSpec>()
    private var symbolSize by Delegates.notNull<Int>()
    private val bindings = mutableListOf<KSDeclaration>()
    private val classDeclarationMap = mutableMapOf<String, KSClassDeclaration>()
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(
            PagingViewHolder::class.qualifiedName ?: return emptyList()
        )
        val result = symbols.filter { !it.validate() }.toList()
        val list = symbols.filter { it.validate() }.toList()
        symbolSize = list.size
        list.map {
            it.accept(visitor, Unit)
        }
        return result
    }

    inner class BuilderVisitor : KSVisitorVoid() {

        override fun visitClassDeclaration(
            classDeclaration: KSClassDeclaration,
            data: Unit
        ) {
            val packageName = classDeclaration.packageName.asString()
            val parameters =
                classDeclaration.primaryConstructor?.parameters?.takeIf { it.isNotEmpty() }
                    ?: throw KSPException("parameters nullOrEmpty")
            val binding =
                parameters.first().type.resolve().declaration

            val jump =
                parameters.last().name?.asString() ?: throw KSPException("jump null")

            val funSpec = FunSpec.builder("create${classDeclaration.simpleName.asString()}")
                .addParameter("parent", ClassName("android.view", "ViewGroup"))
                .addParameter(
                    "jump",
                    LambdaTypeName.get(
                        parameters = arrayOf(String::class.asTypeName()),
                        returnType = Unit::class.asTypeName()
                    )
                )
                .addModifiers(KModifier.PUBLIC)
                .addStatement(
                    "val binding = %N.inflate(LayoutInflater.from(parent.context), parent, false)",
                    binding.simpleName.asString()
                )
                .addStatement("return %N(binding,%N)", classDeclaration.simpleName.asString(), jump)
                .returns(classDeclaration.toClassName())
                .build()

            val cardType = classDeclaration.annotations.first().arguments.first().value as String
            classDeclarationMap[cardType] = classDeclaration
            funSpecs.add(funSpec)
            bindings.add(binding)

            if (funSpecs.size == symbolSize) {
                val fileName = "ViewHolderFactoryKtx"
                val fileSpecBuilder =
                    FileSpec.builder(packageName, fileName)
                        .addImport("android.view", "LayoutInflater")
                        .addType(generateViewHolder())
                        .addType(createViewHolderFactory())
                        .addType(convertCardType2ViewType())
                bindings.forEach {
                    fileSpecBuilder.addImport(it.packageName.asString(), it.simpleName.asString())
                }
                fileSpecBuilder.build().writeTo(codeGenerator, true)
            }
        }

        private fun generateViewHolder(): TypeSpec {
            return TypeSpec.objectBuilder("ViewHolderGenerator")
                .addFunctions(funSpecs)
                .build()
        }

        private fun createViewHolderFactory(): TypeSpec {
            val returnValue = classDeclarationMap.values.first()
                .getAllSuperTypes()
                .toList()
                .dropLast(2)
                .last().declaration as KSClassDeclaration
            val funSpecBuilder = FunSpec.builder("create")
                .addParameter("viewType", Int::class)
                .addParameter("parent", ClassName("android.view", "ViewGroup"))
                .addParameter(
                    "jump",
                    LambdaTypeName.get(
                        parameters = arrayOf(String::class.asTypeName()),
                        returnType = Unit::class.asTypeName()
                    )
                )
                .addModifiers(KModifier.PUBLIC)
                .returns(returnValue.toClassName().parameterizedBy(Any::class.asTypeName()))
                .beginControlFlow("return when (viewType)")

            classDeclarationMap.forEach { (cardType, ksClassDeclaration) ->
                funSpecBuilder.addStatement(
                    "%L->ViewHolderGenerator.create%N(parent,jump)", cardType.hashCode(),
                    ksClassDeclaration.simpleName.asString()
                )
            }
            funSpecBuilder.addStatement(
                "else -> ViewHolderGenerator.createDefaultViewHolder(parent,jump)"
            )
            val funSpec = funSpecBuilder.endControlFlow()
                .build()

            return TypeSpec.objectBuilder("ViewHolderFactory")
                .addFunction(funSpec)
                .build()
        }

        private fun convertCardType2ViewType(): TypeSpec {
            val funSpecBuilder = FunSpec.builder("convertCardType2ViewType")
                .addParameter("cardType", String::class)
                .addModifiers(KModifier.PUBLIC)
                .returns(Int::class)
                .beginControlFlow("return when (cardType)")

            classDeclarationMap.forEach { (cardType, _) ->
                funSpecBuilder.addStatement(
                    "%S->%L", cardType, cardType.hashCode()
                )
            }
            funSpecBuilder.addStatement("else -> %L", "".hashCode())
            val funSpec = funSpecBuilder.endControlFlow()
                .build()

            return TypeSpec.objectBuilder("Converter")
                .addFunction(funSpec)
                .build()
        }
    }

    class Provider : SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            return ViewHolderProcessor(environment.codeGenerator)
        }
    }
}