package com.example.bd_nosql.service;

import com.example.bd_nosql.dto.UsuarioDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

@Service
public class UsuarioService {

    @Autowired
    private DB db;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String salvarUsuario(UsuarioDto dto) throws IOException {
        if (emailJaExiste(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String id = UUID.randomUUID().toString();
        String json = objectMapper.writeValueAsString(dto);
        db.put(bytes("usuario:" + id), bytes(json));
        return id;
    }

    public String autenticarERetornarId(String email, String senha) {
        try (DBIterator iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String chave = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (chave.startsWith("usuario:")) {
                    String valor = new String(iterator.peekNext().getValue(), StandardCharsets.UTF_8);
                    UsuarioDto usuario = new ObjectMapper().readValue(valor, UsuarioDto.class);
                    if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                        return chave.replace("usuario:", "");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
        return null;
    }

    private boolean emailJaExiste(String email) {
        try (DBIterator iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String chave = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (chave.startsWith("usuario:")) {
                    String valor = new String(iterator.peekNext().getValue(), StandardCharsets.UTF_8);
                    UsuarioDto usuario = objectMapper.readValue(valor, UsuarioDto.class);
                    if (usuario.getEmail().equalsIgnoreCase(email)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao verificar e-mail existente", e);
        }
        return false;
    }

    public Map<String, UsuarioDto> listarTodosUsuarios() {
        Map<String, UsuarioDto> usuarios = new HashMap<>();
        try (DBIterator iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String chave = new String(iterator.peekNext().getKey(), StandardCharsets.UTF_8);
                if (chave.startsWith("usuario:")) {
                    String valor = new String(iterator.peekNext().getValue(), StandardCharsets.UTF_8);
                    UsuarioDto usuario = objectMapper.readValue(valor, UsuarioDto.class);
                    String id = chave.replace("usuario:", "");
                    usuarios.put(id, usuario);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
        return usuarios;
    }

    public void deletarUsuario(String id) {
        db.delete(bytes("usuario:" + id));
    }
}