java package dao;

import config.DatabaseConnection;
import models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();
            System.out.println("Usuario agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Usuario actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
package main;

import dao.UsuarioDAO;
import models.Usuario;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Insertar usuario
        Usuario nuevoUsuario = new Usuario(0, "Juan Pérez", "juan@example.com");
        usuarioDAO.insertarUsuario(nuevoUsuario);

        // Obtener usuarios
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + ", Nombre: " + u.getNombre() + ", Email: " + u.getEmail());
        }

        // Actualizar usuario
        if (!usuarios.isEmpty()) {
            Usuario usuarioActualizado = new Usuario(usuarios.get(0).getId(), "Juan P. López", "juanp@example.com");
            usuarioDAO.actualizarUsuario(usuarioActualizado);
        }

        // Eliminar usuario
        if (!usuarios.isEmpty()) {
            usuarioDAO.eliminarUsuario(usuarios.get(0).getId());
        }
    }
}

