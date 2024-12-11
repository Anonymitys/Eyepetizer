package com.ekko.uploader.di

import javax.inject.Qualifier
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.BINARY)
annotation class VideoPageScope


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class VideoPageCoroutineScope
