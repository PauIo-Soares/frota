package br.edu.fateczl.frota.solicitacao;

import java.util.List;

public record DistanciaMatrixResponse(List<String> destination_addresses, List<String> origin_addresses, List<Row> rows,
                                      String status) {
    public record Row(List<Element> elements) {
    }

    public record Element(Distance distance, Duration duration, String status) {
    }

    public record Distance(String text, Long value) {
    }

    public record Duration(String text, Long value) {
    }

}