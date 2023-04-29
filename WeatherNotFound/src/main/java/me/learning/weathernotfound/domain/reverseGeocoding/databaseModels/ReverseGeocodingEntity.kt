package me.learning.weathernotfound.domain.reverseGeocoding.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_reverse_geocoding")
data class ReverseGeocodingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entity_id")
    val entityId: Long? = null,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "coordinate_name")
    val coordinateName:String,
    @ColumnInfo(name = "country_name")
    val countryName:String,
    /* Coordinate name in different languages: */
    @ColumnInfo(name ="af")
    val af: String?,
    @ColumnInfo(name ="am")
    val am: String?,
    @ColumnInfo(name ="an")
    val an: String?,
    @ColumnInfo(name ="ar")
    val ar: String?,
    @ColumnInfo(name ="az")
    val az: String?,
    @ColumnInfo(name ="ba")
    val ba: String?,
    @ColumnInfo(name ="be")
    val be: String?,
    @ColumnInfo(name ="bg")
    val bg: String?,
    @ColumnInfo(name ="bn")
    val bn: String?,
    @ColumnInfo(name ="bo")
    val bo: String?,
    @ColumnInfo(name ="br")
    val br: String?,
    @ColumnInfo(name ="bs")
    val bs: String?,
    @ColumnInfo(name ="ca")
    val ca: String?,
    @ColumnInfo(name ="cs")
    val cs: String?,
    @ColumnInfo(name ="cv")
    val cv: String?,
    @ColumnInfo(name ="cy")
    val cy: String?,
    @ColumnInfo(name ="da")
    val da: String?,
    @ColumnInfo(name ="de")
    val de: String?,
    @ColumnInfo(name ="el")
    val el: String?,
    @ColumnInfo(name ="en")
    val en: String?,
    @ColumnInfo(name ="eo")
    val eo: String?,
    @ColumnInfo(name ="es")
    val es: String?,
    @ColumnInfo(name ="et")
    val et: String?,
    @ColumnInfo(name ="eu")
    val eu: String?,
    @ColumnInfo(name ="fa")
    val fa: String?,
    @ColumnInfo(name ="fi")
    val fi: String?,
    @ColumnInfo(name ="fo")
    val fo: String?,
    @ColumnInfo(name ="fr")
    val fr: String?,
    @ColumnInfo(name ="fy")
    val fy: String?,
    @ColumnInfo(name ="ga")
    val ga: String?,
    @ColumnInfo(name ="gd")
    val gd: String?,
    @ColumnInfo(name ="gl")
    val gl: String?,
    @ColumnInfo(name ="he")
    val he: String?,
    @ColumnInfo(name ="hi")
    val hi: String?,
    @ColumnInfo(name ="hr")
    val hr: String?,
    @ColumnInfo(name ="ht")
    val ht: String?,
    @ColumnInfo(name ="hu")
    val hu: String?,
    @ColumnInfo(name ="hy")
    val hy: String?,
    @ColumnInfo(name ="ia")
    val ia: String?,
    @ColumnInfo(name ="id")
    val id: String?,
    @ColumnInfo(name ="ie")
    val ie: String?,
    @ColumnInfo(name ="io")
    val io: String?,
    @ColumnInfo(name ="is")
    val `is`: String?,
    @ColumnInfo(name ="it")
    val it: String?,
    @ColumnInfo(name ="ja")
    val ja: String?,
    @ColumnInfo(name ="ka")
    val ka: String?,
    @ColumnInfo(name ="kk")
    val kk: String?,
    @ColumnInfo(name ="kl")
    val kl: String?,
    @ColumnInfo(name ="kn")
    val kn: String?,
    @ColumnInfo(name ="ko")
    val ko: String?,
    @ColumnInfo(name ="ku")
    val ku: String?,
    @ColumnInfo(name ="kw")
    val kw: String?,
    @ColumnInfo(name ="la")
    val la: String?,
    @ColumnInfo(name ="lb")
    val lb: String?,
    @ColumnInfo(name ="lt")
    val lt: String?,
    @ColumnInfo(name ="lv")
    val lv: String?,
    @ColumnInfo(name ="mi")
    val mi: String?,
    @ColumnInfo(name ="mk")
    val mk: String?,
    @ColumnInfo(name ="ml")
    val ml: String?,
    @ColumnInfo(name ="mn")
    val mn: String?,
    @ColumnInfo(name ="mr")
    val mr: String?,
    @ColumnInfo(name ="ms")
    val ms: String?,
    @ColumnInfo(name ="my")
    val my: String?,
    @ColumnInfo(name ="nl")
    val nl: String?,
    @ColumnInfo(name ="nn")
    val nn: String?,
    @ColumnInfo(name ="no")
    val no: String?,
    @ColumnInfo(name ="oc")
    val oc: String?,
    @ColumnInfo(name ="or")
    val or: String?,
    @ColumnInfo(name ="os")
    val os: String?,
    @ColumnInfo(name ="pa")
    val pa: String?,
    @ColumnInfo(name ="pl")
    val pl: String?,
    @ColumnInfo(name ="ps")
    val ps: String?,
    @ColumnInfo(name ="pt")
    val pt: String?,
    @ColumnInfo(name ="rm")
    val rm: String?,
    @ColumnInfo(name ="ro")
    val ro: String?,
    @ColumnInfo(name ="ru")
    val ru: String?,
    @ColumnInfo(name ="sc")
    val sc: String?,
    @ColumnInfo(name ="se")
    val se: String?,
    @ColumnInfo(name ="sk")
    val sk: String?,
    @ColumnInfo(name ="sl")
    val sl: String?,
    @ColumnInfo(name ="so")
    val so: String?,
    @ColumnInfo(name ="sq")
    val sq: String?,
    @ColumnInfo(name ="sr")
    val sr: String?,
    @ColumnInfo(name ="sv")
    val sv: String?,
    @ColumnInfo(name ="sw")
    val sw: String?,
    @ColumnInfo(name ="ta")
    val ta: String?,
    @ColumnInfo(name ="te")
    val te: String?,
    @ColumnInfo(name ="tg")
    val tg: String?,
    @ColumnInfo(name ="th")
    val th: String?,
    @ColumnInfo(name ="tk")
    val tk: String?,
    @ColumnInfo(name ="tl")
    val tl: String?,
    @ColumnInfo(name ="tr")
    val tr: String?,
    @ColumnInfo(name ="tt")
    val tt: String?,
    @ColumnInfo(name ="ug")
    val ug: String?,
    @ColumnInfo(name ="uk")
    val uk: String?,
    @ColumnInfo(name ="ur")
    val ur: String?,
    @ColumnInfo(name ="uz")
    val uz: String?,
    @ColumnInfo(name ="vi")
    val vi: String?,
    @ColumnInfo(name ="vo")
    val vo: String?,
    @ColumnInfo(name ="yi")
    val yi: String?,
    @ColumnInfo(name ="yo")
    val yo: String?,
    @ColumnInfo(name ="zh")
    val zh: String?,
    @ColumnInfo(name ="feature_name")
    val featureName: String,
    @ColumnInfo(name ="ascii")
    val ascii: String,
)
