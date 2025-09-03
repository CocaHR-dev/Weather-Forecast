package com.weatherapp.util

fun formatTable(headers: List<String>, rows: List<List<String>>): String {
    val colWidths = headers.mapIndexed { i, h ->
        maxOf(h.length, rows.maxOf { it[i].length })
    }

    val sb = StringBuilder()

    headers.forEachIndexed { i, h -> sb.append(h.padEnd(colWidths[i] + 2)) }
    sb.append("\n")

    colWidths.forEach { sb.append("-".repeat(it + 2)) }
    sb.append("\n")

    rows.forEach { row ->
        row.forEachIndexed { i, cell ->
            sb.append(cell.padEnd(colWidths[i] + 2))
        }
        sb.append("\n")
    }

    return sb.toString()
}