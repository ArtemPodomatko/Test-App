package ru.aapodomatko.testapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.aapodomatko.testapp.data.NewsRepository
import ru.aapodomatko.testapp.models.NewsResponse
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        getAllNews()
    }

    val businessNewsLiveData = MutableLiveData<NewsResponse>()
    val entertainmentNewsLiveData = MutableLiveData<NewsResponse>()
    val generalNewsLiveData = MutableLiveData<NewsResponse>()
    val healthNewsLiveData = MutableLiveData<NewsResponse>()
    val scienceNewsLiveData = MutableLiveData<NewsResponse>()
    val sportsNewsLiveData = MutableLiveData<NewsResponse>()
    val technologyNewsLiveData = MutableLiveData<NewsResponse>()



    private fun getAllNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

            for (category in categories) {
                val result = repository.getNews(category)
                if (result.isSuccessful) {
                    when (category) {
                        "business" -> businessNewsLiveData.postValue(result.body())
                        "entertainment" -> entertainmentNewsLiveData.postValue(result.body())
                        "general" -> generalNewsLiveData.postValue(result.body())
                        "health" -> healthNewsLiveData.postValue(result.body())
                        "science" -> scienceNewsLiveData.postValue(result.body())
                        "sports" -> sportsNewsLiveData.postValue(result.body())
                        "technology" -> technologyNewsLiveData.postValue(result.body())
                    }
                }

                // Добавляем задержку в 1 секунду после каждого запроса
                delay(1000)
            }
        }
    }


}