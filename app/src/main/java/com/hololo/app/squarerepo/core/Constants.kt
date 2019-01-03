package com.hololo.app.squarerepo.core

object Constants {

    const val API_URL = "https://api.github.com"
    const val PAGE_SIZE = 25

    object IntentNames {
        const val PROJECT_ENTITY = "project_entity"
        const val LIST_FRAGMENT_TYPE = "list_fragment_type"
    }

    object ListFragmentType {
        const val REPO = 1
        const val BOOKMARK = 2
    }


}