package space.chuumong.imagesearch.di

import org.koin.dsl.module
import space.chuumong.imagesearch.viewmodel.SearchImageViewModel

val viewModelModule = module {

    factory { SearchImageViewModel(get()) }
}
