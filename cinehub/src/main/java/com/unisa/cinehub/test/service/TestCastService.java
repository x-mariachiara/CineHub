package com.unisa.cinehub.test.service;

import com.unisa.cinehub.control.CatalogoControl;
import com.unisa.cinehub.data.entity.Cast;
import com.unisa.cinehub.data.repository.CastRepository;
import com.unisa.cinehub.model.exception.BeanNotExsistException;
import com.unisa.cinehub.model.exception.InvalidBeanException;
import com.unisa.cinehub.model.service.CastService;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.context.annotation.Bean;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.*;

@SpringBootTest
public class TestCastService {

    @Autowired
    private CastService castService;

    @MockBean
    private CastRepository castRepository;

    @Test
    public void addCast_castValid() throws InvalidBeanException {
        Mockito.when(castRepository.save(any(Cast.class))).thenAnswer(i -> {
            Cast cast = i.getArgument(0, Cast.class);
            cast.setId(1L);
            return cast;
        });
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        assertEquals(cast, castService.addCast(cast));
    }

    @Test
    public void addCast_castNull()  {
        assertThrows(InvalidBeanException.class, () -> castService.addCast(null));
    }

    @Test
    public void addCast_castSenzaNome() {
        assertThrows(InvalidBeanException.class, () -> castService.addCast(new Cast("", "Ercolino")));
    }

    @Test
    public void addCast_castSenzaCognome() {
        assertThrows(InvalidBeanException.class, () -> castService.addCast(new Cast("Andrea", "")));
    }

    @Test
    public void removeCast_valid()  {
        Mockito.doNothing().when(castRepository).deleteById(1L);
        Mockito.when(castRepository.existsById(anyLong())).thenReturn(true);
        try {
            castService.removeCast(1L);
            assert true;
        } catch (BeanNotExsistException e) {
            assert false;
        }
    }

    @Test
    public void removeCast_invalidIdNull() {
        Mockito.doNothing().when(castRepository).deleteById(1L);
        assertThrows(BeanNotExsistException.class, () -> castService.removeCast(null));
    }

    @Test
    public void removeCast_invalidIdNotPresent() {
        Mockito.doNothing().when(castRepository).deleteById(1L);
        Mockito.when(castRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(BeanNotExsistException.class, () -> castService.removeCast(1L));
    }

    @Test
    public void retrieveByKey_valid() throws BeanNotExsistException {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        Mockito.when(castRepository.findById(1L)).thenReturn(Optional.of(cast));
        assertEquals(cast, castService.retrieveByKey(1L));
    }

    @Test
    public void retrieveByKey_idNull() {
        assertThrows(BeanNotExsistException.class, () -> castService.retrieveByKey(null));
    }

    @Test
    public void retrieveByKey_castNotExists(){
        Mockito.when(castRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BeanNotExsistException.class, () -> castService.retrieveByKey(1L));
    }

    @Test
    public void mergeCast_valid() throws InvalidBeanException {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        Mockito.when(castRepository.existsById(anyLong())).thenReturn(true);
        Mockito.when(castRepository.save(any(Cast.class))).thenAnswer(i -> {
            Cast c = i.getArgument(0, Cast.class);
            c.setId(1L);
            return c;
        });
        assertEquals(cast, castService.mergeCast(cast));
    }

    @Test
    public void mergeCast_castIsNull() throws InvalidBeanException {
        assertThrows(InvalidBeanException.class, () -> castService.mergeCast(null));
    }

    @Test
    public void mergeCast_nomeIsBlank() throws InvalidBeanException {
        assertThrows(InvalidBeanException.class, () -> castService.mergeCast(new Cast("", "Ercolino")));
    }

    @Test
    public void mergeCast_cognomeIsBlank() throws InvalidBeanException {
        assertThrows(InvalidBeanException.class, () -> castService.mergeCast(new Cast("Andrea", "")));
    }

    @Test
    public void mergeCast_castNotExistsYet() throws InvalidBeanException {
        Cast cast = new Cast("Kevin", "Spacey");
        cast.setId(1L);
        Mockito.when(castRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(InvalidBeanException.class, () -> castService.mergeCast(cast));
    }




}
