package org.mfigueroa.App.service;

import org.mfigueroa.App.dto.ClienteDTO;
import org.mfigueroa.App.mappers.ClienteMapper;
import org.mfigueroa.App.model.Cliente;
import org.mfigueroa.App.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;


    public ClienteDTO guardarCliente(ClienteDTO clienteDTO) {
            Cliente cliente = ClienteMapper.toEntity(clienteDTO);
            cliente = clienteRepository.save(cliente);
            return ClienteMapper.toDto(cliente);
    }

    // Método para crear múltiples clientes
    public void crearClientes(List<ClienteDTO> clientesDTO) {
        for (ClienteDTO clienteDTO : clientesDTO) {
            Cliente cliente = new Cliente();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setEmail(clienteDTO.getEmail());
            clienteRepository.save(cliente); // Guardamos el cliente en la base de datos
        }
    }

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAllClientes();
        List<ClienteDTO> clienteDTOList = clientes.stream().map(
                c -> ClienteMapper.toDto(c)
        ).toList();
        return clienteDTOList;
    }

    public List<ClienteDTO> findAllClientes(){
        List<Cliente> clientes = clienteRepository.findAllClientes();
        List<ClienteDTO> clienteDTOList = clientes.stream().map(
                c -> ClienteMapper.toDto(c)
        ).toList();
        return clienteDTOList;
    }

    public ClienteDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByCorreo(email)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return ClienteMapper.toDto(cliente);
    }

    public List<ClienteDTO> findByNombre(String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombre(nombre);
        List<ClienteDTO> clienteDTOList = clientes.stream().map(
                c -> ClienteMapper.toDto(c)
        ).toList();
        return clienteDTOList;

    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDetails) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow( ()-> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setEmail(clienteDetails.getEmail());
        cliente = clienteRepository.save(cliente);
        return ClienteMapper.toDto(cliente);
    }

    public boolean updateClienteNombre(Long id, String nombre) {
        int updated = clienteRepository.updateClienteNombre(id, nombre);
        return updated > 0;
    }

    public ClienteDTO deleteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        clienteRepository.deleteById(id);
        return ClienteMapper.toDto(cliente);
    }

    public ClienteDTO deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Cliente no encntrado"));
        clienteRepository.deleteById(id);
        return ClienteMapper.toDto(cliente);
    }

}
