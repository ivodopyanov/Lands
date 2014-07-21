/**
 * 
 */
package lands.players;

import java.util.Set;

import lands.model.Color;
import lands.model.Game;
import lands.model.Player;
import lands.model.PlayerArea;
import lands.model.move.EffectTarget;
import lands.model.move.Move;

/**
 * @author ivodopynov
 * @since 21 июля 2014 г.
 *
 */
public class GeneticPlayerController extends PlayerControllerImpl
{
    private static final int HIDDEN_NEURON_COUNT = 50;
    private static final int PLAYER_DISP = 60;

    //Вход - (4 бита на кол-во)*5 (кол-во цветов)*3 (кол-во зон)*N(кол-во игроков)+N (макс. длина стека)*3(код цвета)*4 (бита на макс. кол-во игроков)
    private static int getInputSize(int playerCount)
    {
        return 80 * playerCount;
    }

    //Выход - 5 (кол-во цветов)

    private final double[][] neuralNet;
    private final int neuronNum;
    private final int inputSize;

    public GeneticPlayerController(Player player, int playerCount)
    {
        super(player);
        inputSize = getInputSize(playerCount);
        neuronNum = HIDDEN_NEURON_COUNT + 5;

        neuralNet = new double[inputSize + neuronNum][neuronNum];
    }

    public GeneticPlayerController(Player player, int playerCount, double[][] neuralNet)
    {
        super(player);
        inputSize = getInputSize(playerCount);
        neuronNum = HIDDEN_NEURON_COUNT + 5;
        this.neuralNet = neuralNet;
    }

    public double[][] getNeuralNet()
    {
        return neuralNet;
    }

    @Override
    public EffectTarget selectEffectTarget(Move move, Set<EffectTarget> effectTargets, Game game)
    {
        return null;
    }

    @Override
    public Move selectMove(Set<Move> moves, Game game)
    {
        int[] input = convertGameToNNInput(game);
        int[] neuronState = new int[neuronNum];
        for (int i = 0; i < neuronNum; i++)
        {
            double result = 0;
            for (int j = 0; j < inputSize; j++)
            {
                result += neuralNet[j][i] * input[j];
            }
            for (int j = 0; j < i; j++)
            {
                result += neuralNet[j + inputSize][i] * neuronState[j];
            }
            if (result > 0.5)
            {
                neuronState[i] = 1;
            }
        }
        int colorId = neuronState[neuronNum - 3] + neuronState[neuronNum - 2] * 2 + neuronState[neuronNum - 1];
        Color color = Color.getColorById(colorId);
        for (Move move : moves)
        {
            if (move.getColor() == color)
            {
                return move;
            }
        }
        return moves.iterator().next();
    }

    private int[] convertGameToNNInput(Game game)
    {
        int[] result = new int[inputSize];
        int pos = 0;
        for (int player = 0; player < game.getPlayers().size(); player++)
        {
            PlayerArea area = game.getPlayerAreas().get(player);
            for (Color color : Color.legalColors())
            {
                int num = area.getHand().get(color).getQuantity();
                for (int i = 0; i < 4; i++)
                {
                    result[pos++] = num % 2;
                    num = num / 2;
                }
            }
            for (Color color : Color.legalColors())
            {
                int num = area.getBattlefield().get(color).getQuantity();
                for (int i = 0; i < 4; i++)
                {
                    result[pos++] = num % 2;
                    num = num / 2;
                }
            }
            for (Color color : Color.legalColors())
            {
                int num = area.getGraveyard().get(color).getQuantity();
                for (int i = 0; i < 4; i++)
                {
                    result[pos++] = num % 2;
                    num = num / 2;
                }
            }
        }
        for (int i = 0; i < game.getPlayers().size(); i++)
        {
            if (i < game.getStack().size())
            {
                int owner = game.getStack().get(i).getOwner();
                int color = game.getStack().get(i).getColor().getId();
                for (int j = 0; j < 3; j++)
                {
                    result[pos++] = color % 2;
                    color = color / 2;
                }
                for (int j = 0; j < 4; j++)
                {
                    result[pos++] = owner % 2;
                    owner = owner % 2;
                }
            }
            else
            {
                for (int j = 0; j < 3; j++)
                {
                    result[pos++] = 0;
                }
                for (int j = 0; j < 4; j++)
                {
                    result[pos++] = 0;
                }
            }
        }
        return result;
    }
}
