create table public.product
(
    id                     bigserial         not null primary key,
    description            varchar(50)       not null,
    price                  decimal           not null
        check ( price <=  1500),
    quantity_in_stock      integer           not null,
    wholesale_product      boolean           not null
);

create table public.discount_card
(
    id                     bigserial         not null primary key,
    number                 integer           not null,
    amount                 smallint          not null
        check ( amount <=  50)
);

INSERT INTO public.product(
    id, description, price, quantity_in_stock, wholesale_product)
VALUES (1, 'Milk', 1.07, 10, true),
       (2, 'Cream 400g', 2.10, 7, true),
       (3, 'Yogurt 400g', 2.10, 7, true),
       (4, 'Packed potatoes 1kg', 1.47, 30, true),
       (5, 'Packed cabbage 1kg', 1.19, 15, true);

INSERT INTO public.discount_card(
    id, "number", amount)
VALUES (1, 1111, 10),
       (2, 2222, 10),
       (3, 3333, 7),
       (4, 4444, 4),
       (5, 5555, 5);