package com.bank.tabungan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data @NoArgsConstructor @AllArgsConstructor
public class Tabungan {
    @Id
    @SequenceGenerator(
            name = "nomor_nasabah_sequence",
            sequenceName = "nomor_nasabah_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nomor_nasabah_sequence"
    )
    private Integer nomorNasabah;
    private Integer jumlah;
    private String jenis;

}
