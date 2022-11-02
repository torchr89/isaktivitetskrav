package no.nav.syfo.oppfolgingstilfelle.kafka

import no.nav.syfo.domain.PersonIdent
import no.nav.syfo.oppfolgingstilfelle.domain.Oppfolgingstilfelle
import no.nav.syfo.util.nowUTC
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

data class KafkaOppfolgingstilfellePerson(
    val uuid: String,
    val createdAt: OffsetDateTime,
    val personIdentNumber: String,
    val oppfolgingstilfelleList: List<KafkaOppfolgingstilfelle>,
    val referanseTilfelleBitUuid: String,
    val referanseTilfelleBitInntruffet: OffsetDateTime,
)

data class KafkaOppfolgingstilfelle(
    val arbeidstakerAtTilfelleEnd: Boolean,
    val start: LocalDate,
    val end: LocalDate,
    val virksomhetsnummerList: List<String>,
)

fun KafkaOppfolgingstilfellePerson.toLatestOppfolgingstilfelle(): Oppfolgingstilfelle? =
    oppfolgingstilfelleList.maxByOrNull {
        it.start
    }?.let { this.toOppfolgingstilfelle(it) }

fun KafkaOppfolgingstilfellePerson.toOppfolgingstilfelle(
    latestOppfolgingsTilfelle: KafkaOppfolgingstilfelle,
) = Oppfolgingstilfelle(
    uuid = UUID.fromString(this.uuid),
    createdAt = nowUTC(),
    personIdent = PersonIdent(this.personIdentNumber),
    tilfelleGenerert = this.createdAt,
    tilfelleStart = latestOppfolgingsTilfelle.start,
    tilfelleEnd = latestOppfolgingsTilfelle.end,
    referanseTilfelleBitUuid = UUID.fromString(this.referanseTilfelleBitUuid),
    referanseTilfelleBitInntruffet = this.referanseTilfelleBitInntruffet,
)
