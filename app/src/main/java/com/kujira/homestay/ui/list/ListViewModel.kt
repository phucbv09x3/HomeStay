package com.kujira.homestay.ui.list

import com.kujira.homestay.data.model.RepoModel
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.ListLiveData
import com.kujira.homestay.utils.hasValue

/**
 * Created by OpenYourEyes on 11/2/20
 */
class ListViewModel : BaseViewModel() {
    val listRepo = ListLiveData<RepoModel>()
    private val _listRepo = listRepo

//    fun listRepoHasValue(): Boolean {
//        return _listRepo.hasValue()
//    }
//
//    fun getListRepo() {
//        executeRequestTest(request = {
//            getRepoGit()
//        }, response = {
//            _listRepo.postValue(it.items)
//        })
//    }

}