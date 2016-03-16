package me.ryleykimmel.brandywine.game.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import io.netty.channel.ChannelFutureListener;
import me.ryleykimmel.brandywine.game.GameService;
import me.ryleykimmel.brandywine.game.model.player.Player;
import me.ryleykimmel.brandywine.network.game.GameSession;
import me.ryleykimmel.brandywine.network.msg.impl.LoginResponseMessage;

/**
 * A {@link Runnable} worker which manages {@link AuthenticationRequest}s.
 */
public final class AuthenticationWorker implements Runnable {

  /**
   * The Logger for this class.
   */
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationWorker.class);

  /**
   * The service used to queue game requests.
   */
  private final GameService service;

  /**
   * The strategy used to authenticate {@link AuthenticationRequest}s.
   */
  private final AuthenticationStrategy strategy;

  /**
   * The request to authenticate.
   */
  private final AuthenticationRequest request;

  /**
   * Constructs a new {@link AuthenticationWorker}.
   *
   * @param service The service used to queue game requests.
   * @param strategy The strategy used to authenticate {@link AuthenticationRequest}s.
   * @param request The request to authenticate.
   */
  public AuthenticationWorker(GameService service, AuthenticationStrategy strategy,
      AuthenticationRequest request) {
    this.service = Preconditions.checkNotNull(service, "GameService may not be null.");
    this.strategy = Preconditions.checkNotNull(strategy, "AuthenticationStrategy may not be null.");
    this.request = Preconditions.checkNotNull(request, "AuthenticationRequest may not be null.");
  }

  @Override
  public void run() {
    GameSession session = request.getSession();

    Player player = new Player(session, request.getCredentials(), service.getWorld());

    try {
      AuthenticationResponse response = strategy.authenticate(player);

      if (response.getStatus() != LoginResponseMessage.STATUS_OK) {
        closeWithResponse(session, response.getStatus());
        return;
      }

      if (service.isPlayerOnline(player)) {
        closeWithResponse(session, LoginResponseMessage.STATUS_ACCOUNT_ONLINE);
        return;
      }

      if (!service.queuePlayer(player)) {
        closeWithResponse(session, LoginResponseMessage.STATUS_SERVER_FULL);
        return;
      }

    } catch (Exception cause) {
      logger.error("Error occured while authenticating.", cause);
      closeWithResponse(session, LoginResponseMessage.STATUS_COULD_NOT_COMPLETE);
    }
  }

  /**
   * Closes the specified GameSession after sending the specified response code.
   * 
   * @param session The GameSession to close.
   * @param response The response to send.
   */
  private void closeWithResponse(GameSession session, int response) {
    session.writeAndFlush(new LoginResponseMessage(response))
        .addListener(ChannelFutureListener.CLOSE);
  }

}
