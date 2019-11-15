package space.chuumong.imagesearch.di

import org.koin.dsl.module
import space.chuumong.data.mapper.SearchImageMapper
import space.chuumong.data.remote.datasource.SearchImageRemoteDataSource
import space.chuumong.data.repositories.SearchImageRepositoryImpl
import space.chuumong.domain.repisotries.SearchImageRepository

val searchImageModule = module {
    factory { SearchImageMapper() }

    factory { SearchImageRemoteDataSource(get()) }

    factory { SearchImageRepositoryImpl(get(), get()) as SearchImageRepository }
}
