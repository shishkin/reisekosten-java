import { Report, Travel } from '@/model';
import { InteractionObject, Matchers } from '@pact-foundation/pact';
import { pactWith } from 'jest-pact';
import supertest from 'supertest';

pactWith(
  {
    consumer: 'Travel Expenses Web UI',
    provider: 'Travel Expenses Backend',
  },
  provider => {
    describe('Submit claims', () => {
      it('accepts claim', async () => {
        const interaction: InteractionObject = {
          state: 'Accounting without claims',
          uponReceiving: 'Travel expense claim with data',
          withRequest: {
            method: 'POST',
            path: '/submit',
            headers: {
              'Content-Type': 'application/json',
            },
            body: {
              start: Matchers.iso8601DateTimeWithMillis(),
              end: Matchers.iso8601DateTimeWithMillis(),
              destination: Matchers.string('Destination'),
              reason: Matchers.string('Reason'),
            },
          },
          willRespondWith: {
            status: 202,
          },
        };

        await provider.addInteraction(interaction);

        const client = () => {
          const url = provider.mockService.baseUrl;
          return supertest(url);
        };

        await client()
          .post('/submit')
          .set('Content-Type', 'application/json')
          .send({
            start: new Date(),
            end: new Date(),
            destination: 'Destination',
            reason: 'Reason',
          })
          .expect(202);
      });
    });

    describe('Expense report', () => {
      describe('without submitted claims', () => {
        it('returns empty report', async () => {
          const interaction: InteractionObject = {
            state: 'Accounting without claims',
            uponReceiving: 'Report request',
            withRequest: {
              method: 'GET',
              path: '/report',
              headers: {
                Accept: 'application/json',
              },
            },
            willRespondWith: {
              status: 200,
              headers: { 'Content-Type': 'application/json' },
              body: {
                totalSum: 0,
                travels: [],
              },
            },
          };

          await provider.addInteraction(interaction);

          const client = () => {
            const url = provider.mockService.baseUrl;
            return supertest(url);
          };

          await client()
            .get('/report')
            .set('Accept', 'application/json')
            .expect(200);
        });
      });

      describe('with 2 submitted claims', () => {
        it('returns report with 2 travels and total sum', async () => {
          const interaction: InteractionObject = {
            state: 'Accounting with 2 claims',
            uponReceiving: 'Report request',
            withRequest: {
              method: 'GET',
              path: '/report',
              headers: {
                Accept: 'application/json',
              },
            },
            willRespondWith: {
              status: 200,
              headers: { 'Content-Type': 'application/json' },
              body: {
                totalSum: Matchers.integer(18),
                travels: Matchers.like([
                  {
                    start: Matchers.iso8601DateTime(),
                    reason: Matchers.string('Reason 1'),
                    destination: Matchers.string('Destination 1'),
                    allowance: 6,
                  },
                  {
                    start: Matchers.iso8601DateTime(),
                    reason: Matchers.string('Reason 2'),
                    destination: Matchers.string('Destination 2'),
                    allowance: 12,
                  },
                ]),
              },
            },
          };

          await provider.addInteraction(interaction);

          const client = () => {
            const url = provider.mockService.baseUrl;
            return supertest(url);
          };

          await client()
            .get('/report')
            .set('Accept', 'application/json')
            .expect(200);
        });
      });
    });
  },
);
