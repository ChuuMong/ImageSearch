package space.chuumong.imagesearch.di

import org.koin.dsl.module
import space.chuumong.domain.usecases.MoreSearchImages
import space.chuumong.domain.usecases.SearchImages

val useCaseModule = module {
    factory { SearchImages(get()) }

    factory { MoreSearchImages(get()) }
}