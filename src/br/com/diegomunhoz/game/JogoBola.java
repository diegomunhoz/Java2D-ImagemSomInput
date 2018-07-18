package br.com.diegomunhoz.game;

import br.com.diegomunhoz.core.Game;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// Esta é a classe que representa nosso jogo. Ela é derivada de "Game", que
// possui o motor do jogo e chama os métodos abaixo quando necessário.
// Ela também implementa as interfaces de ouvinte de teclado (KeyListener),
// botões do mouse (MouseListener) e movimento do mouse (MouseMotion).
public class JogoBola extends Game implements KeyListener, MouseListener,
        MouseMotionListener {

    // Variáveis necessárias para este jogo.
    private Point circle;
    private Point speed;
    // BufferedImage é uma classe do Java para armazenar imagens.
    private BufferedImage imgBola;
    private BufferedImage imgPilarVermelho;
    private BufferedImage imgPilarAmarelo;
    // AudioClip é uma classe do Java para armazenar clips de áudio.
    AudioClip musica;
    HashMap<Integer, Boolean> keyPool;
    int mouseX;
    int mouseY;
    int mouseScroll;

    public JogoBola() {
        // Constructor da classe. Adiciona esta classe como ouvinte dos eventos
        // da janela do jogo.
        getMainWindow().addKeyListener(this);
        getMainWindow().addMouseListener(this);
        getMainWindow().addMouseMotionListener(this);
        keyPool = new HashMap<Integer, Boolean>();
        circle = new Point(10, 10);
        speed = new Point(5, 5);
    }

    public void onLoad() {
        // Carrega as imagens e áudio necessários para o jogo, lembrando
        // que este método é chamado antes do jogo iniciar.
        try {
            URL imgUrl = getClass().getResource("/ball.png");
            if (imgUrl == null) {
                throw new RuntimeException(
                        "A imagem /ball.png não foi encontrada.");
            } else {
                imgBola = ImageIO.read(imgUrl);
            }
            imgUrl = getClass().getResource("/pilar_vermelho.png");
            if (imgUrl == null) {
                throw new RuntimeException(
                        "A imagem /pilar_vermelho.png não foi encontrada.");
            } else {
                imgPilarVermelho = ImageIO.read(imgUrl);
            }
            imgUrl = getClass().getResource("/pilar_amarelo.png");
            if (imgUrl == null) {
                throw new RuntimeException(
                        "A imagem /pilar_amarelo.png não foi encontrada.");
            } else {
                imgPilarAmarelo = ImageIO.read(imgUrl);
            }
            URL url = getClass().getResource("/bgmusic.wav");
            musica = Applet.newAudioClip(url);
            // Após carregar a música, coloca ela para tocar em loop.
            musica.loop();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public void onUnload() {
        // Após o término do jogo, este método é chamado.
        // Pára a execução da música.
        musica.stop();
    }

    public void onUpdate() {
        // Aqui é atualizada a lógica do jogo.
        if (keyPool.get(MouseEvent.BUTTON1) != null) {
            circle.x = mouseX;
            circle.y = mouseY;
        } else {
            if (keyPool.get(KeyEvent.VK_UP) != null) {
                circle.y -= speed.y;
            }
            if (keyPool.get(KeyEvent.VK_DOWN) != null) {
                circle.y += speed.y;
            }
            if (keyPool.get(KeyEvent.VK_LEFT) != null) {
                circle.x -= speed.x;
            }
            if (keyPool.get(KeyEvent.VK_RIGHT) != null) {
                circle.x += speed.x;
            }
        }
        if (keyPool.get(KeyEvent.VK_ESCAPE) != null) {
            terminate();
        }
        try {
            Thread.sleep(20);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoBola.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void onRender(Graphics2D g) {
        // Desenha a tela do jogo.
        g.drawImage(imgPilarAmarelo, 130, 0, null);
        g.drawImage(imgPilarAmarelo, 550, 0, null);
        g.drawImage(imgPilarAmarelo, 710, 0, null);
        g.drawImage(imgBola, circle.x, circle.y, null);
        g.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.8f));
        g.drawImage(imgPilarVermelho, 50, 0, null);
        g.drawImage(imgPilarVermelho, 450, 0, null);
        g.setComposite(AlphaComposite.SrcOver);
    }

    // Os métodos abaixo são as implementações das interfaces incluídas acima.
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        // Chamado quando uma tecla é pressionada.
        keyPool.put(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        // Chamado quando uma tecla é solta.
        keyPool.remove(e.getKeyCode());
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        // Chamado quando um botão do mouse é pressionado.
        keyPool.put(e.getButton(), true);
    }

    public void mouseReleased(MouseEvent e) {
        // Chamado quando um botão do mouse é solto.
        keyPool.remove(e.getButton());
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        // Chamado quando o mouse é movido com o botão pressionado.
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        // Chamado quando o mouse é movido.
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
