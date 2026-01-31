package com.sicradi.votacao.interfaces.rest.dto;

public class VoteResultResponse {
    private long totalSim;
    private long totalNao;
    private double percentualSim;
    private double percentualNao;

    public VoteResultResponse(long totalSim, long totalNao, double percentualSim, double percentualNao) {
        this.totalSim = totalSim;
        this.totalNao = totalNao;
        this.percentualSim = percentualSim;
        this.percentualNao = percentualNao;
    }

    public long getTotalSim() { return totalSim; }
    public void setTotalSim(long totalSim) { this.totalSim = totalSim; }
    public long getTotalNao() { return totalNao; }
    public void setTotalNao(long totalNao) { this.totalNao = totalNao; }
    public double getPercentualSim() { return percentualSim; }
    public void setPercentualSim(double percentualSim) { this.percentualSim = percentualSim; }
    public double getPercentualNao() { return percentualNao; }
    public void setPercentualNao(double percentualNao) { this.percentualNao = percentualNao; }
}
