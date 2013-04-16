package test.client;

/*
 * Smart GWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * Smart GWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  Smart GWT is also
 * available under typical commercial license terms - see
 * http://smartclient.com/license
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

import com.smartgwt.client.data.MultiSortCallback;
import com.smartgwt.client.data.MultiSortDialog;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

import com.google.gwt.core.client.EntryPoint;

public class MultilevelSortSample implements EntryPoint {

    protected boolean isTopIntro() {
        return true;
    }

    public void onModuleLoad() {
        VLayout layout = new VLayout(10);

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth(700);
        listGrid.setHeight(300);
        listGrid.setDataSource(ItemSupplyXmlDs.getInstance());
        listGrid.setAutoFetchData(true);
        listGrid.setCanMultiSort(true);
        // NOTE: the setup for the SortSpecifier bug that should bomb here but doesn't.
        listGrid.setInitialSort(new SortSpecifier[]{
                new SortSpecifier("category", SortDirection.ASCENDING),
                new SortSpecifier("SKU", SortDirection.DESCENDING)
        });

        IButton button = new IButton("Multilevel Sort");
        button.setIcon("crystal/16/actions/sort_incr.png");
        button.setWidth(120);
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                MultiSortDialog.askForSort(listGrid, listGrid.getSort(), new MultiSortCallback() {
                    public void execute(SortSpecifier[] sortLevels) {
                        //if sortLevels is null, it means that the Cancel button was clicked
                        //in which case we simply want to dismiss the dialog
                        if (sortLevels != null) {
                            listGrid.setSort(sortLevels);
                        }
                    }
                });
            }
        });

        final SortSpecifier[] sortSpecifiers = new SortSpecifier[]{
                new SortSpecifier("category", SortDirection.ASCENDING),
                new SortSpecifier("unitCost", SortDirection.DESCENDING)
        };

        IButton sortButton = new IButton("Fixed SortSpecifier");
        sortButton.setWidth(130);
        sortButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {
              // NOTE: this is where I would expect it to bomb with SortSpecifier bug.
                listGrid.setSort(sortSpecifiers);
            }
        });

        layout.addMember(button);
        layout.addMember(sortButton);
        layout.addMember(listGrid);
        layout.draw();
    }

}
