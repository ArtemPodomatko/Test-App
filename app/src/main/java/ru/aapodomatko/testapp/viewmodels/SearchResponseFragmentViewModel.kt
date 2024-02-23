package ru.aapodomatko.testapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aapodomatko.testapp.data.NewsRepository
import ru.aapodomatko.testapp.models.NewsResponse
import javax.inject.Inject

@HiltViewModel
class SearchResponseFragmentViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val searchNewsResponse = MutableLiveData<NewsResponse>()

    fun searchNews(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchNews(query = query)
            if (response.isSuccessful) {
                searchNewsResponse.postValue(response.body())
            }
        }
    }
}