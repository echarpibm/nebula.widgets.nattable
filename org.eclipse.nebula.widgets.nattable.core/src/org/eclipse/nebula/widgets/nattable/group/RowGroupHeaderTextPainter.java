/*******************************************************************************
 * Copyright (c) 2012, 2013 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.group;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.group.config.DefaultRowGroupHeaderLayerConfiguration;
import org.eclipse.nebula.widgets.nattable.group.model.IRowGroupModel;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.CellPainterWrapper;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

public class RowGroupHeaderTextPainter<T> extends CellPainterWrapper {

	/**
	 * Creates the default {@link RowGroupHeaderTextPainter} that uses a {@link TextPainter}
	 * as base {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the bottom
	 * edge of the cell.
	 * @param rowGroupModel the row group model that is used by the grid
	 * 
	 * @deprecated Use constructor without IRowGroupModel reference
	 */
	@Deprecated
	public RowGroupHeaderTextPainter(IRowGroupModel<T> rowGroupModel) {
		this(rowGroupModel, new TextPainter());
	}

	/**
	 * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter}
	 * as base {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the bottom
	 * edge of the cell.
	 * @param rowGroupModel the row group model that is used by the grid
	 * @param interiorPainter the base {@link ICellPainter} to use
	 * 
	 * @deprecated Use constructor without IRowGroupModel reference
	 */
	@Deprecated
	public RowGroupHeaderTextPainter(IRowGroupModel<T> rowGroupModel, ICellPainter interiorPainter) {
		this(interiorPainter, CellEdgeEnum.BOTTOM);
	}

	/**
	 * Creates the default {@link RowGroupHeaderTextPainter} that uses a {@link TextPainter}
	 * as base {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the bottom
	 * edge of the cell.
	 */
	public RowGroupHeaderTextPainter() {
		this(new TextPainter());
	}

	/**
	 * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter}
	 * as base {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the bottom
	 * edge of the cell.
	 * @param interiorPainter the base {@link ICellPainter} to use
	 */
	public RowGroupHeaderTextPainter(ICellPainter interiorPainter) {
		this(interiorPainter, CellEdgeEnum.BOTTOM);
	}

	/**
	 * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter}
	 * as base {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the specified
	 * edge of the cell.
	 * @param interiorPainter the base {@link ICellPainter} to use
	 * @param cellEdge the edge of the cell on which the sort indicator decoration should be applied
	 */
	public RowGroupHeaderTextPainter(ICellPainter interiorPainter, CellEdgeEnum cellEdge) {
		this(interiorPainter, cellEdge, new ExpandCollapseImagePainter(true));
	}

	/**
	 * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter}
	 * as base {@link ICellPainter} and decorate it with the given {@link ICellPainter} to use for sort
	 * related decoration on the specified edge of the cell.
	 * @param interiorPainter the base {@link ICellPainter} to use
	 * @param cellEdge the edge of the cell on which the sort indicator decoration should be applied
	 * @param decoratorPainter the {@link ICellPainter} that should be used to paint the sort related
	 * 			decoration (by default the {@link ExpandCollapseImagePainter} will be used)
	 */
	public RowGroupHeaderTextPainter(ICellPainter interiorPainter, CellEdgeEnum cellEdge, ICellPainter decoratorPainter) {
		setWrappedPainter(new CellPainterDecorator(interiorPainter, cellEdge, decoratorPainter));
	}
	
	//the following constructors are intended to configure the CellPainterDecorator that is created as
	//the wrapped painter of this RowGroupHeaderTextPainter

    /**
     * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter} as base
	 * {@link ICellPainter}. It will use the {@link ExpandCollapseImagePainter} as decorator for sort related 
	 * decorations at the specified cell edge, which can be configured to render the background or 
	 * not via method parameter. With the additional parameters, the behaviour of the created 
	 * {@link CellPainterDecorator} can be configured in terms of rendering.
	 * @param interiorPainter the base {@link ICellPainter} to use
	 * @param cellEdge the edge of the cell on which the sort indicator decoration should be applied
	 * @param paintBg flag to configure whether the {@link ExpandCollapseImagePainter} should paint the background 
	 * 			or not
     * @param spacing the number of pixels that should be used as spacing between cell edge and decoration
     * @param paintDecorationDependent flag to configure if the base {@link ICellPainter} should render
     * 			decoration dependent or not. If it is set to <code>false</code>, the base painter will
     * 			always paint at the same coordinates, using the whole cell bounds, <code>true</code>
     * 			will cause the bounds of the cell to shrink for the base painter.
     */
    public RowGroupHeaderTextPainter(ICellPainter interiorPainter, CellEdgeEnum cellEdge,
    		boolean paintBg, int spacing, boolean paintDecorationDependent) {
    	
	    ICellPainter sortPainter = new ExpandCollapseImagePainter(paintBg);
	    CellPainterDecorator painter = new CellPainterDecorator(interiorPainter, cellEdge, 
	    		spacing, sortPainter, paintDecorationDependent, paintBg);
        setWrappedPainter(painter);
    }
	
	/**
	 * Creates a {@link RowGroupHeaderTextPainter} that uses the given {@link ICellPainter} as base
	 * {@link ICellPainter} and decorate it with the {@link ExpandCollapseImagePainter} on the bottom
	 * edge of the cell. This constructor gives the opportunity to configure the behaviour of the
	 * {@link ExpandCollapseImagePainter} and the {@link CellPainterDecorator} for some attributes.
	 * Remains because of downwards compatibility.
	 * @param interiorPainter the base {@link ICellPainter} to use
	 * @param paintBg flag to configure whether the {@link ExpandCollapseImagePainter} should paint the background 
	 * 			or not
	 * @param interiorPainterToSpanFullWidth flag to configure how the bounds of the base painter should be
	 * 			calculated 
	 */
    public RowGroupHeaderTextPainter(ICellPainter interiorPainter, boolean paintBg, boolean interiorPainterToSpanFullWidth) {
	    ICellPainter sortPainter = new ExpandCollapseImagePainter(paintBg);
	    CellPainterDecorator painter = new CellPainterDecorator(interiorPainter, CellEdgeEnum.BOTTOM, 
	    		0, sortPainter, !interiorPainterToSpanFullWidth, paintBg);
        setWrappedPainter(painter);
	}
	
	
	@Override
	public int getPreferredWidth(ILayerCell cell, GC gc, IConfigRegistry configRegistry) {
		return 0;
	}

	/**
	 * Paints the triangular expand/collapse row header images.
	 */
	protected static class ExpandCollapseImagePainter extends ImagePainter {

		final Image downImg = GUIHelper.getImage("down_0"); //$NON-NLS-1$
		final Image upImg = GUIHelper.getImage("up_0"); //$NON-NLS-1$

		public ExpandCollapseImagePainter(boolean paintBg) {
			super(null, paintBg);
		}

		@Override
		protected Image getImage(ILayerCell cell, IConfigRegistry configRegistry) {
			Image icon = null;

			if (isCollapsed(cell)) {
				icon = downImg;
			} else if (isExpanded(cell)) {
				icon = upImg;
			}

			return icon;
		}

		private boolean isCollapsed(ILayerCell cell) {
			return cell.getConfigLabels().hasLabel(DefaultRowGroupHeaderLayerConfiguration.GROUP_COLLAPSED_CONFIG_TYPE);
		}

		private boolean isExpanded(ILayerCell cell) {
			return cell.getConfigLabels().hasLabel(DefaultRowGroupHeaderLayerConfiguration.GROUP_EXPANDED_CONFIG_TYPE);
		}
	}
}
