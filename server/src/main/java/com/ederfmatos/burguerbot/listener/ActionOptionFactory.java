package com.ederfmatos.burguerbot.listener;

import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Snack;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.request.DrinkService;
import com.ederfmatos.burguerbot.service.request.SnackService;
import org.springframework.stereotype.Component;

@Component
public class ActionOptionFactory {

    private final DrinkService drinkService;
    private final SnackService snackService;
    private final FinishAttendanceService finishAttendanceService;

    public ActionOptionFactory(DrinkService drinkService, SnackService snackService, FinishAttendanceService finishAttendanceService) {
        this.drinkService = drinkService;
        this.snackService = snackService;
        this.finishAttendanceService = finishAttendanceService;
    }

    public ActionExecutable build(Option option) {
        if (option instanceof Drink) {
            return drinkService;
        }

        if (option instanceof Snack) {
            return snackService;
        }

        if (option instanceof FinishAttendanceOption) {
            return finishAttendanceService;
        }

        return null;
    }

}