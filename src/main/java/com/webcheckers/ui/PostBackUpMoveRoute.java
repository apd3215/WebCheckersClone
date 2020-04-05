package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Single_Piece;
import com.webcheckers.model.Space;
import com.webcheckers.util.Message;
import com.webcheckers.model.Move;
import com.webcheckers.Application;
import com.webcheckers.appl.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.WebServer.HOME_URL;


public class PostBackUpMoveRoute implements Route {

    private TemplateEngine templateEngine;

    public PostBackUpMoveRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    // After a move is validated and before it's submitted, once backup is pressed, restore board to what it was
    // at the beginning of the turn.
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        String jsonMove = request.queryParams("actionData");
        Gson gson = new Gson();
        Move move = gson.fromJson(jsonMove, Move.class);
        System.out.println(move.toString());
        Player player = httpSession.attribute("Player");
        Game game = Application.gameCenter.getGameByPlayer(player);
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        Piece.PieceColor color = game.getActiveColor();
        Piece capturedPieceRed = new Single_Piece(Piece.PieceColor.RED);
        Piece capturedPieceWhite = new Single_Piece(Piece.PieceColor.WHITE);
        if (Math.abs(startRow - endRow) != 1){ // check if it's a jump move
            if (endRow < startRow){
                int capturedRow = endRow + 1;
                if ((endCol - startCol) == 2){ // coming from left to right
                    int capturedCol = endCol -1;
                    Space captured = game.getBoardView().getSpace(capturedRow,capturedCol);
                    Space beforeBackup = game.getBoardView().getSpace(endRow,endCol);
                    Space afterBackup = game.getBoardView().getSpace(startRow,startCol);
                    String succMessage = "Backup Successful." +
                            " Before Backup: " + beforeBackup.toString() + " After Backup: " + afterBackup.toString();
                    if (color == Piece.PieceColor.RED){
                        captured.setPiece(capturedPieceWhite);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceRed); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                    else {
                        captured.setPiece(capturedPieceRed);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceWhite); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                }
                else if ((endCol - startCol) == -2){ //coming from right to left
                    int capturedCol = endCol + 1;
                    Space captured = game.getBoardView().getSpace(capturedRow,capturedCol);
                    Space beforeBackup = game.getBoardView().getSpace(endRow,endCol);
                    Space afterBackup = game.getBoardView().getSpace(startRow,startCol);
                    String succMessage = "Backup Successful." +
                            " Before Backup: " + beforeBackup.toString() + " After Backup: " + afterBackup.toString();
                    if (color == Piece.PieceColor.RED){
                        captured.setPiece(capturedPieceWhite);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceRed); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                    else {
                        captured.setPiece(capturedPieceRed);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceRed); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
            }

            } else {
                int capturedRow = endRow -1;
                if ((endCol - startCol) == 2){ //coming from left to right but white's perspective
                    int capturedCol = endCol - 1;
                    Space captured = game.getBoardView().getSpace(capturedRow,capturedCol);
                    Space beforeBackup = game.getBoardView().getSpace(endRow,endCol);
                    Space afterBackup = game.getBoardView().getSpace(startRow,startCol);
                    String succMessage = "Backup Successful." +
                            " Before Backup: " + beforeBackup.toString() + " After Backup: " + afterBackup.toString();
                    if (color == Piece.PieceColor.RED){
                        captured.setPiece(capturedPieceWhite);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceRed); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                    else {
                        captured.setPiece(capturedPieceRed);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceWhite); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                }
                else if ((endCol - startCol) == -2){ // right ot left white view
                    int capturedCol = endCol + 1;
                    Space captured = game.getBoardView().getSpace(capturedRow,capturedCol);
                    Space beforeBackup = game.getBoardView().getSpace(endRow,endCol);
                    Space afterBackup = game.getBoardView().getSpace(startRow,startCol);
                    String succMessage = "Backup Successful." +
                            " Before Backup: " + beforeBackup.toString() + " After Backup: " + afterBackup.toString();
                    if (color == Piece.PieceColor.RED){
                        captured.setPiece(capturedPieceWhite);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceRed); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                    else {
                        captured.setPiece(capturedPieceRed);
                        beforeBackup.setPiece(null);
                        afterBackup.setPiece(capturedPieceWhite); // ignore the fact that i didnt make a new piece for this
                        Message message = Message.info(succMessage);
                        String jsonMessage = gson.toJson(message);
                        response.body(jsonMessage);
                        return jsonMessage;
                    }
                }

            }

        }
        else if (Math.abs((startRow - endRow)) == 1){
            Space beforeBackup = game.getBoardView().getSpace(endRow, endCol);
            Space afterBackup = game.getBoardView().getSpace(startRow,startCol);
            String succMessage = "Backup Successful." +
                    " Before Backup: " + beforeBackup.toString() + " After Backup: " + afterBackup.toString();
            if (color == Piece.PieceColor.RED){
                beforeBackup.setPiece(null);
                afterBackup.setPiece(capturedPieceRed);
                Message message = Message.info(succMessage);
                String jsonMessage = gson.toJson(message);
                response.body(jsonMessage);
                return jsonMessage;
            }
            else{
                beforeBackup.setPiece(null);
                afterBackup.setPiece(capturedPieceWhite);
                Message message = Message.info(succMessage);
                String jsonMessage = gson.toJson(message);
                response.body(jsonMessage);
                return jsonMessage;
            }
        }
        Message message = Message.error("Failure!");
        String jsonMessage = gson.toJson(message);
        response.body(jsonMessage);
        return jsonMessage;
    }
}
