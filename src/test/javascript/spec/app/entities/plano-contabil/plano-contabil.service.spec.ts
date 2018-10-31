/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PlanoContabilService } from 'app/entities/plano-contabil/plano-contabil.service';
import { IPlanoContabil, PlanoContabil } from 'app/shared/model/plano-contabil.model';

describe('Service Tests', () => {
    describe('PlanoContabil Service', () => {
        let injector: TestBed;
        let service: PlanoContabilService;
        let httpMock: HttpTestingController;
        let elemDefault: IPlanoContabil;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PlanoContabilService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PlanoContabil(0, 0, 0, 0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataContratacao: currentDate.format(DATE_TIME_FORMAT),
                        dataEncerramento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a PlanoContabil', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataContratacao: currentDate.format(DATE_TIME_FORMAT),
                        dataEncerramento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataContratacao: currentDate,
                        dataEncerramento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PlanoContabil(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PlanoContabil', async () => {
                const returnedFromService = Object.assign(
                    {
                        funcionarios: 1,
                        socios: 1,
                        faturamentoMensal: 1,
                        dataContratacao: currentDate.format(DATE_TIME_FORMAT),
                        dataEncerramento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataContratacao: currentDate,
                        dataEncerramento: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of PlanoContabil', async () => {
                const returnedFromService = Object.assign(
                    {
                        funcionarios: 1,
                        socios: 1,
                        faturamentoMensal: 1,
                        dataContratacao: currentDate.format(DATE_TIME_FORMAT),
                        dataEncerramento: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataContratacao: currentDate,
                        dataEncerramento: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a PlanoContabil', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
