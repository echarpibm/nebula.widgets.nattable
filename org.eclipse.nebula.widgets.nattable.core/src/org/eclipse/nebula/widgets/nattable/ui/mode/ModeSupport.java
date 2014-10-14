/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.ui.mode;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;

/**
 * Modal event handler for NatTable. This class acts as a proxy event listener.
 * It manages a set of IModeEventHandler instances which control the actual
 * event handling for a given mode. This allows the event handling behavior for
 * different modes to be grouped together and isolated from each other.
 */
public class ModeSupport implements KeyListener, MouseListener,
        MouseMoveListener, MouseTrackListener, FocusListener {

    private Map<String, IModeEventHandler> modeEventHandlerMap = new HashMap<String, IModeEventHandler>();

    private IModeEventHandler currentModeEventHandler;

    public ModeSupport(NatTable natTable) {
        natTable.addKeyListener(this);
        natTable.addMouseListener(this);
        natTable.addMouseMoveListener(this);
        natTable.addMouseTrackListener(this);
        natTable.addFocusListener(this);
    }

    /**
     * Register an event handler to handle events for a given mode.
     * 
     * @param mode
     *            The mode.
     * @param modeEventHandler
     *            An IModeEventHandler instance that will handle events in the
     *            given mode.
     * 
     * @see IModeEventHandler
     */
    public void registerModeEventHandler(String mode,
            IModeEventHandler modeEventHandler) {
        modeEventHandlerMap.put(mode, modeEventHandler);
    }

    /**
     * Switch to the given mode.
     * 
     * @param mode
     *            The target mode to switch to.
     */
    public void switchMode(String mode) {
        if (currentModeEventHandler != null) {
            currentModeEventHandler.cleanup();
        }
        currentModeEventHandler = modeEventHandlerMap.get(mode);
    }

    public void switchMode(IModeEventHandler modeEventHandler) {
        if (currentModeEventHandler != null) {
            currentModeEventHandler.cleanup();
        }
        currentModeEventHandler = modeEventHandler;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        currentModeEventHandler.keyPressed(event);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        currentModeEventHandler.keyReleased(event);
    }

    @Override
    public void mouseDoubleClick(MouseEvent event) {
        currentModeEventHandler.mouseDoubleClick(event);
    }

    @Override
    public void mouseDown(MouseEvent event) {
        currentModeEventHandler.mouseDown(event);
    }

    @Override
    public void mouseUp(MouseEvent event) {
        currentModeEventHandler.mouseUp(event);
    }

    @Override
    public void mouseMove(MouseEvent event) {
        currentModeEventHandler.mouseMove(event);
    }

    @Override
    public void mouseEnter(MouseEvent e) {
        currentModeEventHandler.mouseEnter(e);
    }

    @Override
    public void mouseExit(MouseEvent e) {
        currentModeEventHandler.mouseExit(e);
    }

    @Override
    public void mouseHover(MouseEvent e) {
        currentModeEventHandler.mouseHover(e);
    }

    @Override
    public void focusGained(FocusEvent event) {
        currentModeEventHandler.focusGained(event);
    }

    @Override
    public void focusLost(FocusEvent event) {
        currentModeEventHandler.focusLost(event);
    }

}
