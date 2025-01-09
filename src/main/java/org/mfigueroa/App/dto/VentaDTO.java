package org.mfigueroa.App.dto;

import java.util.List;

public class VentaDTO {

    private Long id;
    private Long clienteId;
    private List<Long> productosId;
    private double total;

    public VentaDTO() {
    }

    public VentaDTO(Long id, Long clienteId, List<Long> productosId, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.productosId = productosId;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getProductosId() {
        return productosId;
    }

    public void setProductosId(List<Long> productosId) {
        this.productosId = productosId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
