package com.ekko.ksp.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class PagingViewHolder(val viewType: String)