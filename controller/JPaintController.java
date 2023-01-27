package controller;

import javax.swing.JButton;

import model.interfaces.IApplicationState;
import view.EventName;
import view.gui.PaintCanvas;
import view.interfaces.IEventCallback;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;

public class JPaintController implements IJPaintController {
    private final IUiModule uiModule;
    private final IApplicationState applicationState;
    private final PaintCanvas  canvas;
    private final IGuiWindow gui;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState,PaintCanvas canvas,IGuiWindow gui) {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
        this.canvas = canvas;
        this.gui = gui;
    }

    @Override
    public void setup() {
        setupEvents();
        setUpListeners();
    }

    private void setUpListeners(){
     undoButton();
     redoButton();
    }

    private void undoButton(){
        JButton undoBtn = gui.getButton(EventName.UNDO);
        undoBtn.addActionListener(((ActionEvent) -> 
        canvas.undo()
        ));
    }

    private void redoButton(){
        JButton redoBtn = gui.getButton(EventName.REDO);
        redoBtn.addActionListener(((ActionEvent) -> 
        canvas.redo()
        ));
    }

    private void setupEvents() {
        uiModule.addEvent(EventName.CHOOSE_SHAPE, () -> applicationState.setActiveShape());
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> applicationState.setActivePrimaryColor());
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> applicationState.setActiveSecondaryColor());
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> applicationState.setActiveShadingType());
        uiModule.addEvent(EventName.CHOOSE_MOUSE_MODE, () -> applicationState.setActiveStartAndEndPointMode());
        uiModule.addEvent(EventName.UNDO, () -> applicationState.undoDraw());
    }
}
