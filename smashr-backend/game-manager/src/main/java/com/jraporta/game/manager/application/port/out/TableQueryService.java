package com.jraporta.game.manager.application.port.out;

import com.jraporta.game.manager.application.model.TableDetails;

public interface TableQueryService {

    public TableDetails getTableDetails(String tableId);

}
