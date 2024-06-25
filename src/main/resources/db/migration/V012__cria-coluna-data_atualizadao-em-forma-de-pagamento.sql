alter table formas_de_pagamento add data_atualizacao datetime null;
update formas_de_pagamento set data_atualizacao = utc_timestamp where true;
alter table formas_de_pagamento modify data_atualizacao datetime not null;