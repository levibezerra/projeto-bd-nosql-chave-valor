package com.example.bd_nosql.service;

import com.example.bd_nosql.dto.PreferenciasDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static org.iq80.leveldb.impl.Iq80DBFactory.asString;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

@Service
public class PreferenciaMusicalService {

    @Autowired
    private DB db;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void salvarNovaPreferencia(String idUsuario, PreferenciasDTO dto) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String chave = "preferencia:" + idUsuario + ":" + uuid;
        String json = objectMapper.writeValueAsString(dto);
        db.put(bytes(chave), bytes(json));
    }

    public PreferenciasDTO buscarPreferencias(String idUsuario) throws IOException {
        byte[] value = db.get(bytes("preferencia:" + idUsuario));
        if (value == null) return null;
        return objectMapper.readValue(asString(value), PreferenciasDTO.class);
    }

    public Map<String, PreferenciasDTO> listarPreferenciasPorUsuario(String idUsuario) {
        Map<String, PreferenciasDTO> resultado = new HashMap<>();
        try (DBIterator iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String chave = asString(iterator.peekNext().getKey());

                if (chave.startsWith("preferencia:" + idUsuario + ":")) {
                    String valor = asString(iterator.peekNext().getValue());
                    PreferenciasDTO dto = new Gson().fromJson(valor, PreferenciasDTO.class);
                    resultado.put(chave.replace("preferencia:", ""), dto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao iterar no banco", e);
        }
        return resultado;
    }

    public Map<String, PreferenciasDTO> listarTodasPreferencias() {
        Map<String, PreferenciasDTO> resultado = new HashMap<>();
        try (DBIterator iterator = db.iterator()) {
            for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
                String chave = asString(iterator.peekNext().getKey());
                if (!chave.startsWith("preferencia:")) continue;
                String valor = asString(iterator.peekNext().getValue());
                PreferenciasDTO dto = new Gson().fromJson(valor, PreferenciasDTO.class);
                resultado.put(chave.replace("preferencia:", ""), dto);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao iterar no banco", e);
        }
        return resultado;
    }

    public void deletarPreferencias(String idUsuario) {
        db.delete(bytes("preferencia:" + idUsuario));
    }
}