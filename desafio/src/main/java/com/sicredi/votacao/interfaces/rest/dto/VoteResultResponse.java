
package com.sicredi.votacao.interfaces.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class VoteResultResponse {

    private long totalSim;
    private long totalNao;
    private double percentualSim;
    private double percentualNao;
    private String title;
    private String description;

}
