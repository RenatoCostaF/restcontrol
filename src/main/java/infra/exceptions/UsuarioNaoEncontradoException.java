package infra.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String nome) {
        super("Usuário " + nome + " não encontrado");
    }
}
