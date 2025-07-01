package com.example.bd_nosql.controller;

import com.example.bd_nosql.dto.PreferenciasDTO;
import com.example.bd_nosql.service.PreferenciaMusicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaMusicalController {

    @Autowired
    private PreferenciaMusicalService preferenciaMusicalService;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<Void> salvarNovaPreferencia(@PathVariable String idUsuario, @RequestBody PreferenciasDTO dto) throws IOException {
        preferenciaMusicalService.salvarNovaPreferencia(idUsuario, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenciasDTO> buscar(@PathVariable String id) throws IOException {
        PreferenciasDTO dto = preferenciaMusicalService.buscarPreferencias(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Map<String, PreferenciasDTO>> listarPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(preferenciaMusicalService.listarPreferenciasPorUsuario(idUsuario));
    }

    @GetMapping
    public ResponseEntity<Map<String, PreferenciasDTO>> listarTodas() {
        return ResponseEntity.ok(preferenciaMusicalService.listarTodasPreferencias());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        preferenciaMusicalService.deletarPreferencias(id);
        return ResponseEntity.noContent().build();
    }
}