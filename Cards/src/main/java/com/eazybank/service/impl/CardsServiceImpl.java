package com.eazybank.service.impl;

import com.eazybank.contants.CardsContants;
import com.eazybank.dto.CardsDto;
import com.eazybank.entity.Cards;
import com.eazybank.exception.CardAlreadyExistsException;
import com.eazybank.exception.ResourceNotFoundException;
import com.eazybank.mapper.CardsMapper;
import com.eazybank.repository.CardsRepository;
import com.eazybank.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {
    private CardsRepository cardsRepository;

    /**
     * @param mobileNumber - takes mobile number as input
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> card = cardsRepository.findByMobileNumber(mobileNumber);
        if(card.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobile number: "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    /**
     *  @param mobileNumber - takes mobile number as input
     *  @return Cards - return the new Card detail
     *  */

    private Cards createNewCard(String mobileNumber){
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setAmountUsed(0);
        newCard.setCardType(CardsContants.CREDIT_CARD);
        newCard.setTotalLimit(CardsContants.NEW_CARD_LIMIT);
        newCard.setAvailableAmount(CardsContants.NEW_CARD_LIMIT);
        return newCard;

    }

    /**
     * @param mobileNumber - takes mobile number as input
     * @return Cards - return the card details
     **/
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardDto(card, new CardsDto());
    }

    /**
     * @param cardsDto - CardsDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards card = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDto, card);
        cardsRepository.save(card);

        return true;
    }

    /**
     * @param mobileNumber - CardsDto Object
     * @return boolean indicating if the delete of card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }


}
