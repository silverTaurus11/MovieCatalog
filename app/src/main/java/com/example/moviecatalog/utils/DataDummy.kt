package com.example.moviecatalog.utils

import com.example.moviecatalog.data.source.remote.response.BaseDetailResponse
import com.example.moviecatalog.data.source.remote.response.GenreEntity
import com.example.moviecatalog.data.source.remote.response.MovieEntity
import com.example.moviecatalog.data.source.remote.response.TvShowEntity

object DataDummy {

    fun getTvShow(id: Int): BaseDetailResponse.TvShowDetailResponse =
        getTvShowsDetailListDummy().find { id == it.id }?: BaseDetailResponse.TvShowDetailResponse()

    fun getTvShowsListDummy(): List<TvShowEntity> = getTvShowsDetailListDummy().map {
        TvShowEntity(it.id, it.name, it.description, it.rating, it.posterPath)
    }

    private fun getTvShowsDetailListDummy(): List<BaseDetailResponse.TvShowDetailResponse>{
        val tvShowEntity = mutableListOf<BaseDetailResponse.TvShowDetailResponse>()
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                1, "Arrow",
                "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                3.75F, listOf(GenreEntity(1, "Action")), "EN", "2012", "2020",
                170, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                2, "Doom Patrol",
                "The adventures of an idealistic mad scientist and his field team of superpowered outcasts.",
                3.95F, listOf(GenreEntity(1, "Action")), "EN", "2019", "2020",
                25, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                3, "Dragon Ball",
                "Son Gokû, a fighter with a monkey tail, goes on a quest with an assortment of odd characters in search of the Dragon Balls, a set of crystals that can give its bearer anything they desire.",
                4.25F, listOf(GenreEntity(2, "Animation")), "JP", "1986", "1989",
                153, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                4, "Fairy Tail",
                "Lucy, an aspiring Celestial Wizard, becomes a friend and ally to powerful wizards Natsu, Gray, and Erza, who are part of the (in)famous wizard guild, Fairy Tail.",
                4F, listOf(GenreEntity(2, "Animation")), "EN", "2009", "2019",
                339, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                5, "Family Guy",
                "In a wacky Rhode Island town, a dysfunctional family strive to cope with everyday life as they are thrown from one crazy scenario to another.",
                4.05F, listOf(GenreEntity(1, "Action")), "EN", "1999", "2020",
                359, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                6, "The Flash",
                "After being struck by lightning, Barry Allen wakes up from his coma to discover he's been given the power of super speed, becoming the next Flash, fighting crime in Central City.",
                3.85F, listOf(GenreEntity(1, "Action")), "EN", "2014", "2020",
                134, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                7, "Gotham",
                "The story behind Detective James Gordon's rise to prominence in Gotham City in the years before Batman's arrival.",
                3.9F, listOf(GenreEntity(1, "Action")), "EN", "2014", "2019",
                25, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                8, "Grey's Anatomy",
                "A drama centered on the personal and professional lives of five surgical interns and their supervisors.",
                3.8F, listOf(GenreEntity(3, "Drama")), "EN", "2015", "2020",
                366, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                9, "Hanna",
                "In equal parts high-concept thriller and coming-of-age drama, HANNA follows the journey of an extraordinary young girl raised in the forest, as she evades the relentless pursuit of an off-book CIA agent and tries to unearth the truth behind who she is.",
                3.75F, listOf(GenreEntity(1, "Action")), "EN", "2019", "2020",
                17, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        tvShowEntity.add(
            BaseDetailResponse.TvShowDetailResponse(
                10, "Iron Fist",
                "A young man is bestowed with incredible martial arts skills and a mystical force known as the Iron Fist.",
                3.25F, listOf(GenreEntity(1, "Action")), "EN", "2017", "2018",
                23, "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )

        return tvShowEntity
    }

    fun getMovie(id: Int?): BaseDetailResponse.MovieDetailResponse
            = getMoviesDetailListDummy().find { id == it.id }?: BaseDetailResponse.MovieDetailResponse()

    fun getMoviesListDummy(): List<MovieEntity>{
        return getMoviesDetailListDummy().map {
            MovieEntity(it.id, it.name, it.description, it.rating, it.posterPath) }
    }

    private fun getMoviesDetailListDummy(): List<BaseDetailResponse.MovieDetailResponse>{
        val movieList = mutableListOf<BaseDetailResponse.MovieDetailResponse>()
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                1, "A Star Is Born",
                "A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.",
                3.8F, listOf(GenreEntity(1, "Action")), "EN", "2018", 136,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                2, "Alita: Battle Angel",
                "A deactivated cyborg's revived, but can't remember anything of her past and goes on a quest to find out who she is.",
                3.65F, listOf(GenreEntity(1, "Action")), "EN", "2019", 122,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                3, "Aquaman",
                "Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land.",
                3.4F, listOf(GenreEntity(1, "Action")), "EN", "2018", 143,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                4, "Bohemian Rhapsody",
                "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid",
                4F,  listOf(GenreEntity(6, "Biography")), "EN", "2018", 134,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                5, "Cold Pursuit",
                "A grieving snowplow driver seeks out revenge against the drug dealers who killed his son.",
                3.1F, listOf(GenreEntity(1, "Action")), "EN", "2019", 119,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                6, "Creed II",
                "Under the tutelage of Rocky Balboa, newly crowned heavyweight champion Adonis Creed faces off against Viktor Drago, the son of Ivan Drago.",
                3.55F, listOf(GenreEntity(5, "Sport")), "EN", "2018", 130,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                7, "Fantastic Beasts: The Crimes of Grindelwald",
                "The second installment of the \"Fantastic Beasts\" series featuring the adventures of Magizoologist Newt Scamander.",
                3.3F, listOf(GenreEntity(2, "Adventure")), "EN", "2018", 134,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                8, "Glass",
                "Security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities.",
                3.35F, listOf(GenreEntity(4, "Drama")), "EN", "2019", 129,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                9, "How to Train Your Dragon: The Hidden World",
                "When Hiccup discovers Toothless isn't the only Night Fury, he must seek \"The Hidden World\", a secret Dragon Utopia before a hired tyrant named Grimmel finds it first.",
                3.75F, listOf(GenreEntity(3, "Animation")), "EN", "2019", 104,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                10, "Avengers: Infinity War",
                "The Avengers and their allies must be willing to sacrifice all in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.",
                4.2F, listOf(GenreEntity(2, "Adventure")), "EN", "2018", 149,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )

        return movieList
    }
}