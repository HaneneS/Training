create table if not exists training
(
    id uuid default schema_training.uuid_generate_v4(),
    name varchar(255) not null,
    description varchar(255) not null,
    price float default null,
    professor varchar(255) default null,
    primary key (id),
    unique(name)
);

create table if not exists appointment
(
    id uuid default schema_training.uuid_generate_v4(),
    start_date_time timestamp with time zone not null,
    end_date_time timestamp with time zone not null,
    training_id uuid,
    primary key (id),
    constraint FK_training foreign key (training_id)
    references training (id)
);

create table if not exists customer
(
    id uuid default schema_training.uuid_generate_v4(),
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    birthdate date not null,
    created_at timestamp with time zone not null,
    primary key (id),
    unique(first_name, last_name, birthdate)
);

create table if not exists customer__appointment
(
    customer_id uuid not null,
    appointment_id uuid not null,
    primary key (customer_id, appointment_id),
    constraint customer__appointment_ibfk_1
    foreign key (appointment_id) references appointment (id),
    constraint customer__appointment_ibfk_2
    foreign key (customer_id) references customer (id)
);