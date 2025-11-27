package br.edu.fateczl.frota.cliente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO dto) {
        if (clienteRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (clienteRepository.findByCpfCnpj(dto.cpfCnpj()).isPresent()) {
            throw new RuntimeException("CPF/CNPJ já cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setCpfCnpj(dto.cpfCnpj());
        cliente.setEndereco(dto.endereco());
        cliente.setCep(dto.cep());

        Cliente salvo = clienteRepository.save(cliente);
        return toDTO(salvo);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        return toDTO(cliente);
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ClienteDTO atualizar(ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.id()).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setCep(dto.cep());

        Cliente atualizado = clienteRepository.save(cliente);
        return toDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), cliente.getCpfCnpj(), cliente.getEndereco(), cliente.getCep());
    }

}