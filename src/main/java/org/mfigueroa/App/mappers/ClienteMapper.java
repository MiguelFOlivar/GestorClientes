package org.mfigueroa.App.mappers;

import org.mfigueroa.App.dto.ClienteDTO;
import org.mfigueroa.App.model.Cliente;

public class ClienteMapper {


    public static ClienteDTO toDto( Cliente cliente ) {
        return new ClienteDTO(
                cliente.getId(), cliente.getNombre(), cliente.getEmail()
        );
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        return cliente;
    }

}
