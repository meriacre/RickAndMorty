package md.merit.rickandmorty.models

data class Result(
    val episode: List<Episode>,
    val image: String,
    val name: String,
    val origin: Origin,
    val status: String
)