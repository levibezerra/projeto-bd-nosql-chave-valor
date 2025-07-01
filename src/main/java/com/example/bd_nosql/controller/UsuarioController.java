package com.example.bd_nosql.controller;

import com.example.bd_nosql.dto.UsuarioDto;
import com.example.bd_nosql.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody UsuarioDto dto) throws IOException {
        try {
            String idGerado = usuarioService.salvarUsuario(dto);
            return ResponseEntity.ok(idGerado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        String idUsuario = usuarioService.autenticarERetornarId(email, senha);
        return idUsuario != null
                ? ResponseEntity.ok(idUsuario)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    @GetMapping
    public ResponseEntity<Map<String, UsuarioDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}