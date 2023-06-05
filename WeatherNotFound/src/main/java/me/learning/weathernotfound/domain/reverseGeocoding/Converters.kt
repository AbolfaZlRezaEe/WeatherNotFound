package me.learning.weathernotfound.domain.reverseGeocoding

import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity
import me.learning.weathernotfound.domain.reverseGeocoding.networkModels.ReversGeocodingResponse
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.LocationCoordinatesModel
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.LocationInfoModel
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.LocationNamesModel
import me.learning.weathernotfound.domain.utils.Utilities

internal object Converters {

    // Network to Database entity converters
    fun reverseGeocodingResponseToReverseGeocodingEntity(
        response: ReversGeocodingResponse
    ): ReverseGeocodingEntity {
        return ReverseGeocodingEntity(
            entityId = null,
            latitude = response.latitude,
            longitude = response.longitude,
            coordinateName = response.coordinateName,
            countryName = response.countryName,
            createdAt = Utilities.getCurrentTime(),
            updatedAt = Utilities.getCurrentTime(),
            af = response.coordinateNameInLanguages.af,
            am = response.coordinateNameInLanguages.am,
            an = response.coordinateNameInLanguages.an,
            ar = response.coordinateNameInLanguages.ar,
            az = response.coordinateNameInLanguages.az,
            ba = response.coordinateNameInLanguages.ba,
            be = response.coordinateNameInLanguages.be,
            bg = response.coordinateNameInLanguages.bg,
            bn = response.coordinateNameInLanguages.bn,
            bo = response.coordinateNameInLanguages.bo,
            br = response.coordinateNameInLanguages.br,
            bs = response.coordinateNameInLanguages.bs,
            ca = response.coordinateNameInLanguages.ca,
            cs = response.coordinateNameInLanguages.cs,
            cv = response.coordinateNameInLanguages.cv,
            cy = response.coordinateNameInLanguages.cy,
            da = response.coordinateNameInLanguages.da,
            de = response.coordinateNameInLanguages.de,
            el = response.coordinateNameInLanguages.el,
            en = response.coordinateNameInLanguages.en,
            eo = response.coordinateNameInLanguages.eo,
            es = response.coordinateNameInLanguages.es,
            et = response.coordinateNameInLanguages.et,
            eu = response.coordinateNameInLanguages.eu,
            fa = response.coordinateNameInLanguages.fa,
            fi = response.coordinateNameInLanguages.fi,
            fo = response.coordinateNameInLanguages.fo,
            fr = response.coordinateNameInLanguages.fr,
            fy = response.coordinateNameInLanguages.fy,
            ga = response.coordinateNameInLanguages.ga,
            gd = response.coordinateNameInLanguages.gd,
            gl = response.coordinateNameInLanguages.gl,
            he = response.coordinateNameInLanguages.he,
            hi = response.coordinateNameInLanguages.hi,
            hr = response.coordinateNameInLanguages.hr,
            ht = response.coordinateNameInLanguages.ht,
            hu = response.coordinateNameInLanguages.hu,
            hy = response.coordinateNameInLanguages.hy,
            ia = response.coordinateNameInLanguages.ia,
            id = response.coordinateNameInLanguages.id,
            ie = response.coordinateNameInLanguages.ie,
            io = response.coordinateNameInLanguages.io,
            /* is = */ response.coordinateNameInLanguages.`is`,
            it = response.coordinateNameInLanguages.it,
            ja = response.coordinateNameInLanguages.ja,
            ka = response.coordinateNameInLanguages.ka,
            kk = response.coordinateNameInLanguages.kk,
            kl = response.coordinateNameInLanguages.kl,
            kn = response.coordinateNameInLanguages.kn,
            ko = response.coordinateNameInLanguages.ko,
            ku = response.coordinateNameInLanguages.ku,
            kw = response.coordinateNameInLanguages.kw,
            la = response.coordinateNameInLanguages.la,
            lb = response.coordinateNameInLanguages.lb,
            lt = response.coordinateNameInLanguages.lt,
            lv = response.coordinateNameInLanguages.lv,
            mi = response.coordinateNameInLanguages.mi,
            mk = response.coordinateNameInLanguages.mk,
            ml = response.coordinateNameInLanguages.ml,
            mn = response.coordinateNameInLanguages.mn,
            mr = response.coordinateNameInLanguages.mr,
            ms = response.coordinateNameInLanguages.ms,
            my = response.coordinateNameInLanguages.my,
            nl = response.coordinateNameInLanguages.nl,
            nn = response.coordinateNameInLanguages.nn,
            no = response.coordinateNameInLanguages.no,
            oc = response.coordinateNameInLanguages.oc,
            or = response.coordinateNameInLanguages.or,
            os = response.coordinateNameInLanguages.os,
            pa = response.coordinateNameInLanguages.pa,
            pl = response.coordinateNameInLanguages.pl,
            ps = response.coordinateNameInLanguages.ps,
            pt = response.coordinateNameInLanguages.pt,
            rm = response.coordinateNameInLanguages.rm,
            ro = response.coordinateNameInLanguages.ro,
            ru = response.coordinateNameInLanguages.ru,
            sc = response.coordinateNameInLanguages.sc,
            se = response.coordinateNameInLanguages.se,
            sk = response.coordinateNameInLanguages.sk,
            sl = response.coordinateNameInLanguages.sl,
            so = response.coordinateNameInLanguages.so,
            sq = response.coordinateNameInLanguages.sq,
            sr = response.coordinateNameInLanguages.sr,
            sv = response.coordinateNameInLanguages.sv,
            sw = response.coordinateNameInLanguages.sw,
            ta = response.coordinateNameInLanguages.ta,
            te = response.coordinateNameInLanguages.te,
            tg = response.coordinateNameInLanguages.tg,
            th = response.coordinateNameInLanguages.th,
            tk = response.coordinateNameInLanguages.tk,
            tl = response.coordinateNameInLanguages.tl,
            tr = response.coordinateNameInLanguages.tr,
            tt = response.coordinateNameInLanguages.tt,
            ug = response.coordinateNameInLanguages.ug,
            uk = response.coordinateNameInLanguages.uk,
            ur = response.coordinateNameInLanguages.ur,
            uz = response.coordinateNameInLanguages.uz,
            vi = response.coordinateNameInLanguages.vi,
            vo = response.coordinateNameInLanguages.vo,
            yi = response.coordinateNameInLanguages.yi,
            yo = response.coordinateNameInLanguages.yo,
            zh = response.coordinateNameInLanguages.zh,
            featureName = response.coordinateNameInLanguages.featureName,
            ascii = response.coordinateNameInLanguages.ascii,
        )
    }

    // Database to Presentation model converters
    fun reverseGeocodingEntityToLocationInfoModel(
        entity: ReverseGeocodingEntity
    ): LocationInfoModel {
        return LocationInfoModel(
            locationName = entity.coordinateName,
            countryName = entity.countryName,
            locationCoordinates = LocationCoordinatesModel(
                latitude = entity.latitude,
                longitude = entity.longitude,
            ),
            locationNameInOtherLanguages = LocationNamesModel(
                af = entity.af,
                am = entity.am,
                an = entity.an,
                ar = entity.ar,
                az = entity.az,
                ba = entity.ba,
                be = entity.be,
                bg = entity.bg,
                bn = entity.bn,
                bo = entity.bo,
                br = entity.br,
                bs = entity.bs,
                ca = entity.ca,
                cs = entity.cs,
                cv = entity.cv,
                cy = entity.cy,
                da = entity.da,
                de = entity.de,
                el = entity.el,
                en = entity.en,
                eo = entity.eo,
                es = entity.es,
                et = entity.et,
                eu = entity.eu,
                fa = entity.fa,
                fi = entity.fi,
                fo = entity.fo,
                fr = entity.fr,
                fy = entity.fy,
                ga = entity.ga,
                gd = entity.gd,
                gl = entity.gl,
                he = entity.he,
                hi = entity.hi,
                hr = entity.hr,
                ht = entity.ht,
                hu = entity.hu,
                hy = entity.hy,
                ia = entity.ia,
                id = entity.id,
                ie = entity.ie,
                io = entity.io,
                /* is = */ entity.`is`,
                it = entity.it,
                ja = entity.ja,
                ka = entity.ka,
                kk = entity.kk,
                kl = entity.kl,
                kn = entity.kn,
                ko = entity.ko,
                ku = entity.ku,
                kw = entity.kw,
                la = entity.la,
                lb = entity.lb,
                lt = entity.lt,
                lv = entity.lv,
                mi = entity.mi,
                mk = entity.mk,
                ml = entity.ml,
                mn = entity.mn,
                mr = entity.mr,
                ms = entity.ms,
                my = entity.my,
                nl = entity.nl,
                nn = entity.nn,
                no = entity.no,
                oc = entity.oc,
                or = entity.or,
                os = entity.os,
                pa = entity.pa,
                pl = entity.pl,
                ps = entity.ps,
                pt = entity.pt,
                rm = entity.rm,
                ro = entity.ro,
                ru = entity.ru,
                sc = entity.sc,
                se = entity.se,
                sk = entity.sk,
                sl = entity.sl,
                so = entity.so,
                sq = entity.sq,
                sr = entity.sr,
                sv = entity.sv,
                sw = entity.sw,
                ta = entity.ta,
                te = entity.te,
                tg = entity.tg,
                th = entity.th,
                tk = entity.tk,
                tl = entity.tl,
                tr = entity.tr,
                tt = entity.tt,
                ug = entity.ug,
                uk = entity.uk,
                ur = entity.ur,
                uz = entity.uz,
                vi = entity.vi,
                vo = entity.vo,
                yi = entity.yi,
                yo = entity.yo,
                zh = entity.zh,
                featureName = entity.featureName,
                ascii = entity.ascii,
            )
        )
    }

    // Network to Presentation model converters
    fun reverseGeocodingResponseToLocationInfoModel(
        response: ReversGeocodingResponse
    ): LocationInfoModel {
        return LocationInfoModel(
            locationName = response.coordinateName,
            countryName = response.countryName,
            locationCoordinates = LocationCoordinatesModel(
                latitude = response.latitude,
                longitude = response.longitude
            ),
            locationNameInOtherLanguages = LocationNamesModel(
                af = response.coordinateNameInLanguages.af,
                am = response.coordinateNameInLanguages.am,
                an = response.coordinateNameInLanguages.an,
                ar = response.coordinateNameInLanguages.ar,
                az = response.coordinateNameInLanguages.az,
                ba = response.coordinateNameInLanguages.ba,
                be = response.coordinateNameInLanguages.be,
                bg = response.coordinateNameInLanguages.bg,
                bn = response.coordinateNameInLanguages.bn,
                bo = response.coordinateNameInLanguages.bo,
                br = response.coordinateNameInLanguages.br,
                bs = response.coordinateNameInLanguages.bs,
                ca = response.coordinateNameInLanguages.ca,
                cs = response.coordinateNameInLanguages.cs,
                cv = response.coordinateNameInLanguages.cv,
                cy = response.coordinateNameInLanguages.cy,
                da = response.coordinateNameInLanguages.da,
                de = response.coordinateNameInLanguages.de,
                el = response.coordinateNameInLanguages.el,
                en = response.coordinateNameInLanguages.en,
                eo = response.coordinateNameInLanguages.eo,
                es = response.coordinateNameInLanguages.es,
                et = response.coordinateNameInLanguages.et,
                eu = response.coordinateNameInLanguages.eu,
                fa = response.coordinateNameInLanguages.fa,
                fi = response.coordinateNameInLanguages.fi,
                fo = response.coordinateNameInLanguages.fo,
                fr = response.coordinateNameInLanguages.fr,
                fy = response.coordinateNameInLanguages.fy,
                ga = response.coordinateNameInLanguages.ga,
                gd = response.coordinateNameInLanguages.gd,
                gl = response.coordinateNameInLanguages.gl,
                he = response.coordinateNameInLanguages.he,
                hi = response.coordinateNameInLanguages.hi,
                hr = response.coordinateNameInLanguages.hr,
                ht = response.coordinateNameInLanguages.ht,
                hu = response.coordinateNameInLanguages.hu,
                hy = response.coordinateNameInLanguages.hy,
                ia = response.coordinateNameInLanguages.ia,
                id = response.coordinateNameInLanguages.id,
                ie = response.coordinateNameInLanguages.ie,
                io = response.coordinateNameInLanguages.io,
                /* is = */ response.coordinateNameInLanguages.`is`,
                it = response.coordinateNameInLanguages.it,
                ja = response.coordinateNameInLanguages.ja,
                ka = response.coordinateNameInLanguages.ka,
                kk = response.coordinateNameInLanguages.kk,
                kl = response.coordinateNameInLanguages.kl,
                kn = response.coordinateNameInLanguages.kn,
                ko = response.coordinateNameInLanguages.ko,
                ku = response.coordinateNameInLanguages.ku,
                kw = response.coordinateNameInLanguages.kw,
                la = response.coordinateNameInLanguages.la,
                lb = response.coordinateNameInLanguages.lb,
                lt = response.coordinateNameInLanguages.lt,
                lv = response.coordinateNameInLanguages.lv,
                mi = response.coordinateNameInLanguages.mi,
                mk = response.coordinateNameInLanguages.mk,
                ml = response.coordinateNameInLanguages.ml,
                mn = response.coordinateNameInLanguages.mn,
                mr = response.coordinateNameInLanguages.mr,
                ms = response.coordinateNameInLanguages.ms,
                my = response.coordinateNameInLanguages.my,
                nl = response.coordinateNameInLanguages.nl,
                nn = response.coordinateNameInLanguages.nn,
                no = response.coordinateNameInLanguages.no,
                oc = response.coordinateNameInLanguages.oc,
                or = response.coordinateNameInLanguages.or,
                os = response.coordinateNameInLanguages.os,
                pa = response.coordinateNameInLanguages.pa,
                pl = response.coordinateNameInLanguages.pl,
                ps = response.coordinateNameInLanguages.ps,
                pt = response.coordinateNameInLanguages.pt,
                rm = response.coordinateNameInLanguages.rm,
                ro = response.coordinateNameInLanguages.ro,
                ru = response.coordinateNameInLanguages.ru,
                sc = response.coordinateNameInLanguages.sc,
                se = response.coordinateNameInLanguages.se,
                sk = response.coordinateNameInLanguages.sk,
                sl = response.coordinateNameInLanguages.sl,
                so = response.coordinateNameInLanguages.so,
                sq = response.coordinateNameInLanguages.sq,
                sr = response.coordinateNameInLanguages.sr,
                sv = response.coordinateNameInLanguages.sv,
                sw = response.coordinateNameInLanguages.sw,
                ta = response.coordinateNameInLanguages.ta,
                te = response.coordinateNameInLanguages.te,
                tg = response.coordinateNameInLanguages.tg,
                th = response.coordinateNameInLanguages.th,
                tk = response.coordinateNameInLanguages.tk,
                tl = response.coordinateNameInLanguages.tl,
                tr = response.coordinateNameInLanguages.tr,
                tt = response.coordinateNameInLanguages.tt,
                ug = response.coordinateNameInLanguages.ug,
                uk = response.coordinateNameInLanguages.uk,
                ur = response.coordinateNameInLanguages.ur,
                uz = response.coordinateNameInLanguages.uz,
                vi = response.coordinateNameInLanguages.vi,
                vo = response.coordinateNameInLanguages.vo,
                yi = response.coordinateNameInLanguages.yi,
                yo = response.coordinateNameInLanguages.yo,
                zh = response.coordinateNameInLanguages.zh,
                featureName = response.coordinateNameInLanguages.featureName,
                ascii = response.coordinateNameInLanguages.ascii,
            )
        )
    }
}
