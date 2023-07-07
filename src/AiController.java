public class AiController {
    public PlayerController playerController;
    public Rect rect;

    public AiController(PlayerController playerController, Rect rect) {
        this.playerController = playerController;
        this.rect = rect;
    }

    public void update(double dt) {
        playerController.update(dt);
        if (rect.y < playerController.rect.y) {
            playerController.moveUp(dt);
        } else if (rect.y + rect.height > playerController.rect.y + playerController.rect.height) {
            playerController.moveDown(dt);
        }
    }
}
