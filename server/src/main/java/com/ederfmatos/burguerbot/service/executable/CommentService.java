package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.Comment;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.enumeration.EmojiEnum;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.CommentOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.repository.CommentRepository;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class CommentService extends ActionService {

    private final CommentRepository commentRepository;

    public CommentService(OptionService optionService, FinishAttendanceService finishAttendanceService, CommentRepository commentRepository) {
        super(optionService, finishAttendanceService);
        this.commentRepository = commentRepository;
    }

    @Override
    protected List<ActionExecutable> getListeners() {
        return Arrays.asList(
                this::getCommentMessage,
                this::handleCommentMessage,
                this::handleNewOrder
        );
    }

    private String handleCommentMessage(MessageRequest messageRequest, Attendance attendance, Option option) {
        Comment comment = new Comment(UUID.randomUUID().toString(), attendance.getCustomer(), messageRequest.getMessage(), LocalDateTime.now());
        commentRepository.save(comment);

        return "Muito obrigado pela mensagem." + EmojiEnum.HEARTH + "\n\n" +
                this.getNewOrderMessage();
    }

    private String getCommentMessage(MessageRequest messageRequest, Attendance attendance, Option option) {
        return "Ok, pode digitar o que deseja!";
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof CommentOption;
    }
}
