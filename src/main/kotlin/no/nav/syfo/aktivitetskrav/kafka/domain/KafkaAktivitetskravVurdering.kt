package no.nav.syfo.aktivitetskrav.kafka.domain

import java.time.LocalDate
import java.time.OffsetDateTime

data class KafkaAktivitetskravVurdering(
    val uuid: String,
    val personIdent: String,
    val createdAt: OffsetDateTime,
    val status: String,
    val beskrivelse: String?,
    val arsaker: List<String>,
    val stoppunktAt: LocalDate,
    val updatedBy: String?,
    val sistVurdert: OffsetDateTime?,
    val frist: LocalDate?,
)
