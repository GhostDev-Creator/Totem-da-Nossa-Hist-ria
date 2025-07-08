import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;
import java.time.*;

@SuppressWarnings("ALL")
public class alguem {

    public static void main(String[] args) {
        abrirInicio();
    }

    // Tela principal (login)
    public static void abrirInicio() {
        JFrame inicio = new JFrame("Totem");

        JLabel intro = new JLabel("Bem vinda(O) ao Totem do [seu nome]");
        intro.setBounds(80, 80, 180, 30);

        JLabel usuario = new JLabel("<html>Por questões de segurança<br>insira seu nome para acessar.");
        usuario.setBounds(80, 120, 180, 50);

        JTextField campu = new JTextField();
        campu.setBounds(80, 175, 180, 30);

        JButton tentar = new JButton("Tente entrar");
        tentar.setBounds(80, 210, 180, 30);
        tentar.addActionListener(e -> {
            String nome = campu.getText();
            if (nome.equals("alguem")) {
                abrirEscolha(nome);
                inicio.dispose();
            } else if (nome.equals("Alguem")) {
                usuario.setText("<html>Por questões de segurança<br>insira seu nome para acessar.(tudo minusculo pfvr)");
                campu.setText("");
            } else {
                abrirNegado(inicio);
            }
        });

        inicio.add(intro);
        inicio.add(usuario);
        inicio.add(campu);
        inicio.add(tentar);

        inicio.setLayout(null);
        inicio.setBounds(550, 130, 400, 600);
        inicio.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inicio.setVisible(true);
    }

    // Tela de opções (depois do login correto)
    public static void abrirEscolha(String nome) {
        JFrame escolha = new JFrame("Escolhas");

        JLabel escolhaop = new JLabel("O que posso fazer por você hoje, " + nome + "?");
        escolhaop.setBounds(80, 130, 250, 30);

        JButton carta = new JButton("\uD83D\uDC8C Uma carta");
        carta.setBounds(100, 190, 180, 30);
        carta.addActionListener(e -> {
            abrirCarta(nome, escolha);
        });

        JButton horas = new JButton("🕰️ Um Relogio");
        horas.setBounds(100, 250, 180, 30);
        horas.addActionListener(e -> {
            abrirRelogio(nome, escolha);
        });

        JButton musica = new JButton("\uD83C\uDFB5 Uma música");
        musica.setBounds(100, 310, 180, 30);
        musica.addActionListener(e -> {
            abrirMusica(nome, escolha);
        });

        JButton info = new JButton("\uD83D\uDCAC Uma informação");
        info.setBounds(100, 370, 180, 30);
        info.addActionListener(e -> {
            abrirLembranca(nome, escolha);
        });

        escolha.add(escolhaop);
        escolha.add(carta);
        escolha.add(horas);
        escolha.add(musica);
        escolha.add(info);

        escolha.setLayout(null);
        escolha.setBounds(550, 130, 400, 600);
        escolha.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        escolha.setVisible(true);
    }

    // Tela da carta
    public static void abrirCarta(String nome, JFrame anterior) {
        JFrame carta = new JFrame("Carta");

        JLabel texto = new JLabel("[texto da carta]");
        texto.setVerticalAlignment(SwingConstants.TOP);
        JScrollPane scroll = new JScrollPane(texto);
        scroll.setBounds(50, 50, 700, 500);

        JButton voltar = new JButton("<-");
        voltar.setBounds(10, 10, 50, 25);
        voltar.addActionListener(e -> {
            carta.dispose();
            abrirEscolha(nome);
        });

        carta.setLayout(null);
        carta.add(voltar);
        carta.add(scroll);
        carta.setBounds(450, 130, 800, 600);
        carta.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        carta.setVisible(true);
        anterior.dispose();
    }

    // Tela de "acesso negado"
    public static void abrirNegado(JFrame anterior) {
        JFrame sai = new JFrame("Estranha");

        JLabel msg = new JLabel("Qual foi, isso aqui não é pra você não!!! Estranha.");
        msg.setBounds(60, 120, 280, 50);

        sai.add(msg);
        sai.setLayout(null);
        sai.setBounds(550, 130, 400, 600);
        sai.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sai.setVisible(true);
        anterior.dispose();

        Timer timer = new Timer(3000, e -> sai.dispose());
        timer.setRepeats(false);
        timer.start();
    }

    public static void abrirRelogio(String nome, JFrame anterior) {
        JFrame relogio = new JFrame("Relógio");

        relogio.setLayout(null);
        relogio.setBounds(500, 130, 400, 600);
        relogio.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Botão de voltar
        JButton voltar = new JButton("<-");
        voltar.setBounds(10, 10, 50, 25);
        voltar.addActionListener(e -> {
            relogio.dispose();
            abrirEscolha(nome);
        });
        relogio.add(voltar);

        // Label para mostrar o tempo decorrido
        JLabel labelTempo = new JLabel("", SwingConstants.CENTER);
        labelTempo.setBounds(20, 100, 360, 100); // espaço maior para texto com quebras
        relogio.add(labelTempo);

        // Data inicial fixa
        LocalDateTime dataInicial = LocalDateTime.of(ano, mes, dia, hora, minutos, segundos);

        // Timer para atualizar a cada 1 segundo
        Timer timer = new Timer(1000, e -> {
            labelTempo.setText(calcularTempoDecorrido(dataInicial));
        });
        timer.setInitialDelay(0);
        timer.start();

        relogio.setVisible(true);
        anterior.dispose();
    }

    private static String calcularTempoDecorrido(LocalDateTime dataInicial) {
        LocalDateTime agora = LocalDateTime.now();

        Period periodo = Period.between(dataInicial.toLocalDate(), agora.toLocalDate());
        Duration duracao = Duration.between(dataInicial.toLocalTime(), agora.toLocalTime());

        int anos = periodo.getYears();
        int meses = periodo.getMonths();
        int dias = periodo.getDays();

        long segundosTotais = duracao.getSeconds();
        int horas = (int) (segundosTotais / 3600);
        int minutos = (int) ((segundosTotais % 3600) / 60);
        int segundos = (int) (segundosTotais % 60);

        if (duracao.isNegative()) {
            dias--;
            Duration duracaoAjustada = duracao.plusDays(1);
            segundosTotais = duracaoAjustada.getSeconds();
            horas = (int) (segundosTotais / 3600);
            minutos = (int) ((segundosTotais % 3600) / 60);
            segundos = (int) (segundosTotais % 60);
        }

        return String.format(
                "<html>Meus dias ficaram melhores desde %s,<br>" +
                        "e eu aprendi que a vida é boa faz<br>" +
                        "%d dias, %d meses, %d anos,<br>" +
                        "e %02d horas, %02d minutos e %02d segundos.<br></html>",
                dataInicial.toLocalDate().toString(),
                dias, meses, anos,
                horas, minutos, segundos
        );
    }

    public static void abrirMusica(String nome, JFrame anterior) {
        JFrame music = new JFrame("Música");

        music.setLayout(null);
        music.setBounds(500, 130, 400, 600);
        music.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton voltar = new JButton("<-");
        voltar.setBounds(10, 10, 50, 25);
        voltar.addActionListener(e -> {
            music.dispose();
            abrirEscolha(nome);
        });
        music.add(voltar);

        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=qU9mHegkTc4")); // Arctic Monkeys - 505
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String[] partes = {
                "Mesmo longe,",
                "você sempre",
                "vai ser",
                "o meu 505."
        };

        JLabel texto = new JLabel("", SwingConstants.CENTER);
        texto.setBounds(50, 150, 300, 30);
        music.add(texto);

        JButton revelar = new JButton("Mostrar frase");
        revelar.setBounds(100, 250, 180, 30);
        music.add(revelar);

        int[] indice = {0};

        revelar.addActionListener(e -> {
            if (indice[0] < partes.length) {
                texto.setText(partes[indice[0]]);
                indice[0]++;
            } else {
                texto.setText("❤");
                revelar.setEnabled(false);
            }
        });

        music.setVisible(true);
        anterior.dispose();
    }

    public static void abrirLembranca(String nome, JFrame anterior) {
        JFrame info = new JFrame("Informações");

        info.setLayout(null);
        info.setBounds(500, 130, 400, 600);
        info.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Botão de voltar
        JButton voltar = new JButton("<-");
        voltar.setBounds(10, 10, 50, 25);
        voltar.addActionListener(e -> {
            info.dispose();
            abrirEscolha(nome);
        });
        info.add(voltar);

        // Área para curiosidades
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBounds(20, 50, 340, 400);
        info.add(scroll);

        // Curiosidades
        String[] curiosidades = {
                [texto com formato =
                "TEXTO 1 --------------------",
                "TEXTO 2 --------------------",
                "TEXTO 3 --------------------"
          ]
        };

        JButton revelar = new JButton("Mostrar curiosidade");
        revelar.setBounds(100, 470, 180, 30);
        info.add(revelar);

        int[] indice = {0};
        revelar.addActionListener(e -> {
            if (indice[0] < curiosidades.length) {
                modelo.addElement(curiosidades[indice[0]]);
                indice[0]++;
            } else {
                revelar.setText("Fim :)");
                revelar.setEnabled(false);
            }
        });

        info.setVisible(true);
        anterior.dispose();
    }
}
