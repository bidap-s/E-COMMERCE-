package com.example.ecommerce.carts;

import com.example.ecommerce.products.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-19T18:39:42+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.20 (Microsoft)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDto toDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setItems( cartItemSetToCartItemDtoList( cart.getItems() ) );
        cartDto.setId( cart.getId() );

        cartDto.setTotalPrice( cart.getTotalPrice() );

        return cartDto;
    }

    @Override
    public CartItemDto toDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setProduct( productToProductDto( cartItem.getProduct() ) );
        if ( cartItem.getQuantity() != null ) {
            cartItemDto.setQuantity( cartItem.getQuantity() );
        }

        cartItemDto.setTotalPrice( cartItem.getTotalPrice() );

        return cartItemDto;
    }

    protected List<CartItemDto> cartItemSetToCartItemDtoList(Set<CartItem> set) {
        if ( set == null ) {
            return null;
        }

        List<CartItemDto> list = new ArrayList<CartItemDto>( set.size() );
        for ( CartItem cartItem : set ) {
            list.add( toDto( cartItem ) );
        }

        return list;
    }

    protected ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( product.getId() );
        productDto.setName( product.getName() );
        productDto.setPrice( product.getPrice() );

        return productDto;
    }
}
