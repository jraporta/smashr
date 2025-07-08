package com.jraporta.game.manager.application.port.out;

import com.jraporta.game.manager.application.model.TableDetails;
import com.jraporta.game.manager.application.exception.TableNotFoundException;
import com.jraporta.game.manager.application.exception.QueryServiceInternalException;

public interface TableQueryService {

    /**
     * @param tableId the id of the table
     * @return the details of the table
     * @throws TableNotFoundException if the table is not found
     * @throws QueryServiceInternalException if unexpected problem with query Service
     */
    public TableDetails getTableDetails(String tableId);

}
