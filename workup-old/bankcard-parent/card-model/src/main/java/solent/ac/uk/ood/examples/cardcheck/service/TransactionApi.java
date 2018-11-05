/* ***************************************************************************
 * Copyright 2018 Craig Gallen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ****************************************************************************/

package solent.ac.uk.ood.examples.cardcheck.service;

import solent.ac.uk.ood.examples.cardcheck.model.Transaction;
import solent.ac.uk.ood.examples.cardcheck.model.TransactionResult;

public interface TransactionApi {

    public TransactionResult requestPreAuthorisation(Transaction requestTransaction);

    public TransactionResult requestTransaction(Transaction transactionRequest);
}
