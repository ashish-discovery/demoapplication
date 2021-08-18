package ashish.be.gupta.firstapplication

import ashish.be.gupta.firstapplication.login.repository.LoginRepository
import ashish.be.gupta.firstapplication.login.viewmodel.LoginViewModel
import ashish.be.gupta.firstapplication.logout.repository.LogoutRepository
import ashish.be.gupta.firstapplication.logout.viewmodel.LogoutViewModel
import ashish.be.gupta.firstapplication.modules.adapter.*
import ashish.be.gupta.firstapplication.modules.repository.*
import ashish.be.gupta.firstapplication.modules.spinneradapter.BuildingSpinnerAdapter
import ashish.be.gupta.firstapplication.modules.spinneradapter.RoomSpinnerAdapter
import ashish.be.gupta.firstapplication.modules.view.*
import ashish.be.gupta.firstapplication.modules.viewmodel.*
import ashish.be.gupta.firstapplication.retrofit.provideApiService
import ashish.be.gupta.firstapplication.retrofit.provideOkHttpClient
import ashish.be.gupta.firstapplication.retrofit.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

val loginLogoutModule = module {
    single { LoginRepository(get()) }
    single { LogoutRepository(get()) }
    factory { LoginViewModel(get()) }
    factory { LogoutViewModel(get()) }
}

val customerModule = module {
    single { CustomerRepository(get()) }
    factory { CustomerViewModel(get()) }

    scope<CustomerListActivity> {
        scoped { BuildingSpinnerAdapter(get()) }
        scoped { CustomerAdapter(get(), get()) }
    }

    scope<SearchCustomerActivity> {
        scoped { SearchCustomerAdapter(get(), get()) }
    }

    scope<CustomerLogsListActivity> {
        scoped { CustomerLogsAdapter(get()) }
    }

    scope<AddEditCustomerActivity> {
        scoped { BuildingSpinnerAdapter(get()) }
        scoped { RoomSpinnerAdapter(get()) }
    }

}

val buildingModule = module {
    single { BuildingRepository(get()) }
    factory { BuildingViewModel(get()) }

}

val roomModule = module {
    single { RoomRepository(get()) }
    factory { RoomViewModel(get()) }

}
//
//val stbPackageModule = module {
//    single { STBPackageRepository(get()) }
//    factory { STBPackageViewModel(get()) }
//
//    scope<AlaCartListActivity> {
//        scoped { AlaCartAdapter(get()) }
//    }
//
//    scope<STBPackageListActivity> {
//        scoped { STBPackageAdapter(get(), get()) }
//    }
//}
//