create table if not exists offer(
	id varchar primary key,
	account_id varchar,
	limit_type varchar,
	limit_value numeric,
	status varchar,
	offer_activation_time timestamp,
	offer_expiry_time timestamp
);

create table if not exists account(
	id varchar primary key,
	customer_id varchar,
	account_limit numeric,
	per_transaction_limit numeric,
	last_account_limit numeric,
	last_per_transaction_limit numeric,
	account_limit_update_time timestamp,
	per_transaction_limit_update_time timestamp
);